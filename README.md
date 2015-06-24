The instructions below will help you setup a development environment and use the Koverse SDK. 

1. Download and install Apache Maven for your operating system.

    * Eclipse and Netbeans both have Maven integrations
    * For command line use:
    * Ensure the mvn and java executable directories are in your PATH environment variable. 
    * Set your JAVA_HOME environment variable to the root directory of your Java JDK installation. 

1. Optionally - Configure Maven and Koverse to build and deploy your Addon in a single command.

    * In your Koverse instance, use the System Administration app's "API" tab to create a new API Token.
    * Add the new API Token to the Administrators group
    * Add the following XML to your ~/.m2/settings.xml file.
    * Replace the API-TOKEN-HERE and KOVERSE-SERVER-HERE text as necessary. 
    * Your server may need http or https.
```<settings>
        <profiles>
                <profile>
                        <activation>
                                <activeByDefault>true</activeByDefault>
                        </activation>
                        <properties>
                                <koverse.apitoken>API-TOKEN-HERE</koverse.apitoken>
                                <koverse.serverurl>https://<KOVERSE-SERVER-HERE>/Koverse</koverse.serverurl>
                        </properties>
                </profile>
        </profiles>
</settings>
```
4. Download or fork the koverse-sdk-project from GitHub
    * http://github.com/Koverse/koverse-sdk-project
    * Be sure to switch to the correct branch for your version of Koverse

1. Edit the example Java and/or HTML/JS files

    * See /src/main/java/... for example Java classes
    * See /src/test/java/... for example JUnit tests.
        * Use these classes to debug your code locally. 
    * See /src/main/resources/apps/... for example HTML/JS applications
    * Update the src/main/resources/classesToInspect file to reflect your classses.
1. Build and deploy your new addon 
    *  If you completed optional step #2 above, use this command from the root directory of your koverse-sdk-project.

        `mvn clean package koverse:deploy`

    * Otherwise, use this command. Then upload the addon, using the System Administration app's "Addons" tab. 

    `mvn clean package`
1. Use the Data Flow app to configure and use custom sources, transforms and sinks. 
