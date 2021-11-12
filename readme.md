Unfortunetely I was unable to pack my solution into the dockerfile, so it has to be run locally:

Required
- python3.7
- sbt==1.55
- scala==2.13.6
- Java 8

Go to docker folder, run ``` docker-compose up ``` <br>
Download data folder place it in data folder <br>
Run pip ```pip install -r requirements.txt``` in the scripts folder <br>
Run ```python3 load_data.py``` <br>
Then run ```sbt compile``` in the app folder <br>
To run the app ```sbt "runMain Solution"```
(In case of errors open the app in Intelij IDE and compile and run the Solution main function)

The the solutions should appear in the dataAnalyzed folder in the form of CSV files