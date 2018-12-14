// common plugin to general ansible tasks
def call(Map pipelineParams) {

    node(pipelineParams.agent){
        // cloning public sunbird-devops and private repo
        stage('checkout private repo') {
            dir('sunbird-devops-private'){
            git branch: pipelineParams.branch, url: pipelineParams.scmUrl, credentialsId: pipelineParams.credentials
            }
        }

        stage('ansible') {
            println pipelineParams
            sh """
            ansible-playbook -i sunbird-devops-private/ansible/inventories/${pipelineParams.env} \
            ansible/${pipelineParams.ansiblePlaybook} ${pipelineParams.ansibleExtraArgs}
            """
        }
    }
}
