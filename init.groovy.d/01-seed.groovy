import jenkins.model.*
import hudson.model.*
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition

def j = Jenkins.instance
def scriptFile = new File("/usr/share/demo/Jenkinsfile")
if (!scriptFile.exists()) {
  println "[seed] Jenkinsfile non trovato in /usr/share/demo/Jenkinsfile"
} else {
  println "[seed] Creo/aggiorno job 'AI-Quality-Gate'"
  def jobName = "AI-Quality-Gate"
  def job = j.getItem(jobName)
  def scriptText = scriptFile.text
  if (job == null) {
    job = j.createProject(WorkflowJob, jobName)
  }
  job.setDefinition(new CpsFlowDefinition(scriptText, true))
  job.displayName = "AI Quality Gate (Codex Demo)"
  job.save()
  println "[seed] Job pronto: ${jobName}"
}
