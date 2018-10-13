:: 设置基准文件夹
set "baseDir=D:\Documents\GitHub\"

:: 开始调用mvn命令构建
call mvn clean install -Dmaven.test.skip=true -f "%baseDir%kit\pom.xml
call mvn clean install -Dmaven.test.skip=true -f "%baseDir%httpkit\pom.xml
call mvn clean install -Dmaven.test.skip=true -f "%baseDir%dbkit\pom.xml
call mvn clean package -Dmaven.test.skip=true -f "%baseDir%SWING-POM\pom.xml
call mvn clean package -Dmaven.test.skip=true -f "%baseDir%bigDesk\GDict\pom.xml

pause