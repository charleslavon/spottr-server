
gradle_version=2.9

wget -P /home/ubuntu/ -N https://services.gradle.org/distributions/gradle-${gradle_version}-all.zip
unzip /home/ubuntu/gradle-${gradle_version}-all.zip -d /home/ubuntu/gradle
sudo ln -sfn gradle-${gradle_version} /home/ubuntu/gradle/latest
/home/ubuntu/gradle/latest/bin/gradle -v

export GRADLE_HOME=/home/ubuntu/gradle/latest export
echo $GRADLE_HOME
echo " "
echo " " 
echo "current path is " 
echo $PATH
export PATH=${PATH%:/usr/local/heroku/bin}
export PATH=${PATH%/usr/local/gradle-1.10/bin}
echo " "
echo " " 
echo "trimming ancient version of gradle from path " 
echo $PATH

export PATH=$PATH:$GRADLE_HOME/bin
export PATH=$PATH:/usr/local/heroku/bin

echo " "
echo " " 
echo "final path is  " 
echo $PATH

gradle -v

