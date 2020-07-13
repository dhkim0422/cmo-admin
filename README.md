Insilicogen Web Framework
=====================

## Settting info
------------

#### 1. clone git repository 

        //git을 클론 받기
        git clone --bare https://github.com/dhkim0422/cmo-admin.git
        cd cmo-admin
        
        //새로운 git 프로젝트생성 후 해당 프로젝트 명으로 변경 내역을 저장
        git push --mirror https://github.com/dhkim0422/cmo-name.git
        cd ..
        rm -rf cmo-admin

#### 2. clone local repository 
Paste Repository Path or Url


#### 3. import project from existing local repository
Import as general project

#### 4. configure maven project

#### 5.pom.xml 수정
        <groupId>iwf</groupId>
        <artifactId>iwf</artifactId>
        <packaging>war</packaging>
        <version>1.0.0</version>
        <name>iwf</name>

#### 6.web.xml 수정
        <!-- Display-name 설정 -->
        <display-name>iwf</display-name>
#### 7.org.eclipse.wst.common.component
    <?xml version="1.0" encoding="UTF-8"?><project-modules id="moduleCoreId" project-version="1.5.0">
        <wb-module deploy-name="iwf-1.0.0">
            <wb-resource deploy-path="/" source-path="/target/m2e-wtp/web-resources"/>
            <wb-resource deploy-path="/" source-path="/src/main/webapp" tag="defaultRootSource"/>
            <wb-resource deploy-path="/WEB-INF/classes" source-path="/src/main/java"/>
            <wb-resource deploy-path="/WEB-INF/classes" source-path="/src/main/resources"/>
            <property name="context-root" value="iwf"/>
            <property name="java-output-path" value="/iwf/target/classes"/>
        </wb-module>
    </project-modules>`

#### 8. db 생성 및 연결정보 수정
env-enc-*.xml 수정
