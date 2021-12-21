#!/usr/bin/env bash
 
source ~/.bashrc
source /app/was/tomcat/embedded/bin/setenv.sh
 
CHECK_USER=`${WAS_HOME}/script/chk_user.sh`
 
if [ ${CHECK_USER} == "0" ]; then
    exit 0
fi
 
function logDelete {
  for i in $@;
  do
    echo "Delete Log file ${i}"
    /usr/bin/rm -f $1
  done
}
 
function safeDelete {
  for i in $@;
  do
    echo "Safe Delete log zip file ${i}"
    /usr/bin/cp -f /dev/null $1
  done
}
 
 
## clean
safeDelete "/log/app/vineAppOrderInfo.log"
 
 
TARGET_ZIP_LOG=(`ls /log/app/*.zip 2> /dev/null`)
logDelete $TARGET_ZIP_LOG;
 
 
TARGET_WAS_LOG=(`ls /log/was/access.* | grep -v access.log 2>/dev/null`)
logDelete ${TARGET_WAS_LOG[@]};