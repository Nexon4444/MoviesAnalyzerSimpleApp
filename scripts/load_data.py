import mysql.connector
import os
import pandas as pd
import mysql.connector as msql
from mysql.connector import Error
from sqlalchemy import create_engine
import pymysql
CREATE_SCHEMA_SQL = "CREATE SCHEMA IF NOT EXISTS MOVIES"
data_dir = os.path.join("../data/ml-latest-small")
data_filenames = ["links.csv", "movies.csv", "ratings.csv", "tags.csv"]
all_csvs_df = [(file, pd.read_csv(os.path.join(data_dir, file), index_col=False, delimiter=',')) for file in data_filenames]

try:
    conn = msql.connect(host='localhost', user='root',
                        password='password')

    if conn.is_connected():
        cursor = conn.cursor()

        cursor.execute(CREATE_SCHEMA_SQL)

    engine = create_engine('mysql+pymysql://root:password@127.0.0.1', pool_recycle=3600)
    sqlEngine = engine.connect()

    sql_statements = [df.to_sql(file.split(".")[0], sqlEngine,  schema="MOVIES") for file, df in all_csvs_df]

    print("database is created")

except Error as e:
    print("Error while connecting to MySQL", e)
