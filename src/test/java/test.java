import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionRequirementsGraph;
import org.camunda.bpm.dmn.engine.DmnDecisionRuleResult;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.test.DmnEngineRule;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.commons.utils.IoUtil;
import org.junit.Rule;
import org.junit.Test;

public class test {
    @Rule
    public DmnEngineRule rule = new DmnEngineRule();

    @Test
    public void shouldEvaluateDecision() {
      // Get DMN engine
      //DmnEngine dmnEngine = rule.getDmnEngine();
      DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
      // Parse decision
      InputStream inputStream = IoUtil.fileAsStream("org/kie/example/bmi.dmn");
      //DmnModelInstance dmnModelInstance = Dmn.readModelFromFile(new File("C:/Repos/mybusinessapp/src/main/resources/org/kie/example/TrafficViolation.dmn"));
      DmnDecision decision = dmnEngine.parseDecision("_7CCDD2B2-24B6-4C82-A972-EB17D178C3CC", inputStream);
      //List<DmnDecision> decisions = dmnEngine.parseDecisions(dmnModelInstance);
     
      // Set input variables
      VariableMap variables = Variables.createVariables()
        .putValue("height", 174)
        .putValue("weight", 80);
      

      System.out.println(decision);
      // Evaluate decision with id '_7CCDD2B2-24B6-4C82-A972-EB17D178C3CC' from file 'bmi.dmn'
      DmnDecisionTableResult results = dmnEngine.evaluateDecisionTable(decision, variables);
  
      // Check that one rule has matched
      assertThat(results).hasSize(1);
  
      DmnDecisionRuleResult result = results.getSingleResult();
      assertThat(result)
        .containsOnly(
          entry("result", "notok"),
          entry("reason", "you took too much man, you took too much!")
        );
  
      // Change input variables
      //variables.putValue("status", "gold");
  
      // Evaluate decision again
      //results = dmnEngine.evaluateDecisionTable(decision, variables);
  
      // Check new result
     // assertThat(results).hasSize(1);
  
    //   result = results.getSingleResult();
    //   assertThat(result)
    //     .containsOnly(
    //       entry("result", "ok"),
    //       entry("reason", "you get anything you want")
    //     );
    }
}