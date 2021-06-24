import org.gradle.api.attributes.Attribute
import org.gradle.api.attributes.AttributeDisambiguationRule
import org.gradle.api.attributes.MultipleCandidatesDetails

val artifactType = Attribute.of("artifactType", String::class.java)

class DisambiguationRule : AttributeDisambiguationRule<String> {
  override fun execute(t: MultipleCandidatesDetails<String>) {
    /**
     * The default disambiguation rules exclude the jar variants because they define the `org.gradle.libraryelements`
     *
     * See https://github.com/gradle/gradle/blob/master/subprojects/dependency-management/src/main/java/org/gradle/internal/component/model/MultipleCandidateMatcher.java#L221
     *
     * Not 100% sure why this happens but forcing to use the jar in this specific case fixes the issue
     */
    val jar = t.candidateValues.firstOrNull { it == "jar" }
    if(jar != null) {
      t.closestMatch(jar)
    }
  }
}

dependencies.attributesSchema.attribute(artifactType).disambiguationRules.add(DisambiguationRule::class.java)
