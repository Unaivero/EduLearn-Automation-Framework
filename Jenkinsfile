pipeline {
    agent any
    
    tools {
        maven 'Maven 3.9.5'
        jdk 'JDK 11'
    }
    
    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser to run tests on')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run tests in headless mode')
        string(name: 'TEST_SUITE', defaultValue: 'testng.xml', description: 'TestNG suite to run')
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                sh """
                    mvn test -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS} -DsuiteXmlFile=${params.TEST_SUITE}
                """
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/extent-reports',
                        reportFiles: 'edulearn-test-report.html',
                        reportName: 'Extent Report'
                    ])
                }
            }
        }
    }
    
    post {
        always {
            archiveArtifacts artifacts: 'target/screenshots/**', allowEmptyArchive: true
            archiveArtifacts artifacts: 'target/logs/**', allowEmptyArchive: true
        }
        
        success {
            echo 'Tests executed successfully!'
        }
        
        failure {
            echo 'Tests failed! Check the reports for details.'
        }
    }
}
