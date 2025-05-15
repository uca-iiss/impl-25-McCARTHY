pipeline {
    agent any   // Usar cualquier agente disponible

    environment {
        // Usar variables de entorno del contenedor
        SCALA_HOME = '/usr/share/scala'
        SBT_HOME = '/usr/share/sbt'
        PATH = "${PATH}:${SCALA_HOME}/bin:${SBT_HOME}/bin"
    }

    stages {
        stage('Check') {
            steps {
                script {
                    sh '''
                        git config --global --add safe.directory /var/jenkins_home/workspace/errores-scala                    '''
                }
            }
        }

        stage('Compilar') {
            steps {
                dir('scala') {
                    sh 'sbt -Dsbt.rootdir=true -Dsbt.global.base=project/.sbtboot -Dsbt.boot.directory=project/.boot -Dsbt.ivy.home=project/.ivy2 compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('scala') {
                    sh 'sbt test'
                }
            }
            post {
                always {
                    junit 'scala/target/test-reports/**/*.xml'
                }
            }
        }
    }
}