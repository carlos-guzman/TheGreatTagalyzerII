from dataAnalysisDB import *
import os

jobID = '' #jobID of current EMR cluster

def main():
    #os.system('python -m mrjob.tools.emr.create_job_flow --num-ec2-instances=2')

    DBcon = connectIntoDB()
    cursor = DBcon.cursor()
    getNonAnalyzed('data.csv', DBcon, cursor)
    uploadS3('data.csv')
    os.system('python map_reduce.py -r emr --emr-job-flow-id={} s3://largescale/data/ > results.csv'.format(jobID))
    r = open('results.csv', 'r')
    for line in r:
        entries = line.split()
        entries[0] = entries[0][1:-1]
        if entries[0] != "None":
            updatePostSentiment(entries[1], int(entries[0]), DBcon, cursor)
    r.close()
    print 'done!'

if __name__ == '__main__':
    main()
