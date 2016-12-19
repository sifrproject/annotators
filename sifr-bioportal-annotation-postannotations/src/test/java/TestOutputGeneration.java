import io.github.twktheainur.sparqy.graph.storage.JenaRemoteSPARQLStore;
import io.github.twktheainur.sparqy.graph.storage.StoreHandler;
import io.github.twktheainur.sparqy.graph.store.Store;
import org.junit.Before;
import org.junit.Test;
import org.sifrproject.annotations.api.model.Annotation;
import org.sifrproject.annotations.api.model.AnnotationFactory;
import org.sifrproject.annotations.api.model.retrieval.PropertyRetriever;
import org.sifrproject.annotations.api.output.AnnotatorOutput;
import org.sifrproject.annotations.input.BioPortalJSONAnnotationParser;
import org.sifrproject.annotations.model.BioPortalLazyAnnotationFactory;
import org.sifrproject.annotations.output.LIRMMOutputGeneratorDispatcher;
import org.sifrproject.annotations.model.retrieval.CUIPropertyRetriever;
import org.sifrproject.annotations.model.retrieval.SemanticTypePropertyRetriever;
import org.sifrproject.annotations.umls.UMLSGroupIndex;
import org.sifrproject.annotations.umls.UMLSSemanticGroupsLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TestOutputGeneration {

    private Logger logger = LoggerFactory.getLogger(TestOutputGeneration.class);

    private static final String jsonOutput = "[{\"annotatedClass\":{\"@id\":\"http://purl.lirmm.fr/ontology/MDRFRE/10007050\",\"@type\":\"http://www.w3.org/2002/07/owl#Class\",\"links\":{\"self\":\"http://data.bioportal.lirmm.fr/ontologies/MDRFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMDRFRE%2F10007050\",\"ontology\":\"http://data.bioportal.lirmm.fr/ontologies/MDRFRE\",\"children\":\"http://data.bioportal.lirmm.fr/ontologies/MDRFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMDRFRE%2F10007050/children\",\"parents\":\"http://data.bioportal.lirmm.fr/ontologies/MDRFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMDRFRE%2F10007050/parents\",\"descendants\":\"http://data.bioportal.lirmm.fr/ontologies/MDRFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMDRFRE%2F10007050/descendants\",\"ancestors\":\"http://data.bioportal.lirmm.fr/ontologies/MDRFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMDRFRE%2F10007050/ancestors\",\"instances\":\"http://data.bioportal.lirmm.fr/ontologies/MDRFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMDRFRE%2F10007050/instances\",\"tree\":\"http://data.bioportal.lirmm.fr/ontologies/MDRFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMDRFRE%2F10007050/tree\",\"notes\":\"http://data.bioportal.lirmm.fr/ontologies/MDRFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMDRFRE%2F10007050/notes\",\"mappings\":\"http://data.bioportal.lirmm.fr/ontologies/MDRFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMDRFRE%2F10007050/mappings\",\"ui\":\"http://bioportal.lirmm.fr/ontologies/MDRFRE?p=classes&conceptid=http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMDRFRE%2F10007050\",\"@context\":{\"self\":\"http://www.w3.org/2002/07/owl#Class\",\"ontology\":\"http://data.bioontology.org/metadata/Ontology\",\"children\":\"http://www.w3.org/2002/07/owl#Class\",\"parents\":\"http://www.w3.org/2002/07/owl#Class\",\"descendants\":\"http://www.w3.org/2002/07/owl#Class\",\"ancestors\":\"http://www.w3.org/2002/07/owl#Class\",\"instances\":\"http://data.bioontology.org/metadata/Instance\",\"tree\":\"http://www.w3.org/2002/07/owl#Class\",\"notes\":\"http://data.bioontology.org/metadata/Note\",\"mappings\":\"http://data.bioontology.org/metadata/Mapping\",\"ui\":\"http://www.w3.org/2002/07/owl#Class\"}},\"@context\":{\"@vocab\":\"http://data.bioontology.org/metadata/\"}},\"hierarchy\":[],\"annotations\":[{\"from\":1,\"to\":6,\"matchType\":\"PREF\",\"text\":\"CANCER\"}],\"mappings\":[]},{\"annotatedClass\":{\"@id\":\"http://www.limics.fr/ontologies/ontolurgences#Cancer\",\"@type\":\"http://www.w3.org/2002/07/owl#Class\",\"links\":{\"self\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23Cancer\",\"ontology\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES\",\"children\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23Cancer/children\",\"parents\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23Cancer/parents\",\"descendants\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23Cancer/descendants\",\"ancestors\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23Cancer/ancestors\",\"instances\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23Cancer/instances\",\"tree\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23Cancer/tree\",\"notes\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23Cancer/notes\",\"mappings\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23Cancer/mappings\",\"ui\":\"http://bioportal.lirmm.fr/ontologies/ONTOLURGENCES?p=classes&conceptid=http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23Cancer\",\"@context\":{\"self\":\"http://www.w3.org/2002/07/owl#Class\",\"ontology\":\"http://data.bioontology.org/metadata/Ontology\",\"children\":\"http://www.w3.org/2002/07/owl#Class\",\"parents\":\"http://www.w3.org/2002/07/owl#Class\",\"descendants\":\"http://www.w3.org/2002/07/owl#Class\",\"ancestors\":\"http://www.w3.org/2002/07/owl#Class\",\"instances\":\"http://data.bioontology.org/metadata/Instance\",\"tree\":\"http://www.w3.org/2002/07/owl#Class\",\"notes\":\"http://data.bioontology.org/metadata/Note\",\"mappings\":\"http://data.bioontology.org/metadata/Mapping\",\"ui\":\"http://www.w3.org/2002/07/owl#Class\"}},\"@context\":{\"@vocab\":\"http://data.bioontology.org/metadata/\"}},\"hierarchy\":[],\"annotations\":[{\"from\":1,\"to\":6,\"matchType\":\"PREF\",\"text\":\"CANCER\"}],\"mappings\":[]},{\"annotatedClass\":{\"@id\":\"http://www.limics.fr/ontologies/ontolurgences#TumeurMaligne\",\"@type\":\"http://www.w3.org/2002/07/owl#Class\",\"links\":{\"self\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23TumeurMaligne\",\"ontology\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES\",\"children\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23TumeurMaligne/children\",\"parents\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23TumeurMaligne/parents\",\"descendants\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23TumeurMaligne/descendants\",\"ancestors\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23TumeurMaligne/ancestors\",\"instances\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23TumeurMaligne/instances\",\"tree\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23TumeurMaligne/tree\",\"notes\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23TumeurMaligne/notes\",\"mappings\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23TumeurMaligne/mappings\",\"ui\":\"http://bioportal.lirmm.fr/ontologies/ONTOLURGENCES?p=classes&conceptid=http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23TumeurMaligne\",\"@context\":{\"self\":\"http://www.w3.org/2002/07/owl#Class\",\"ontology\":\"http://data.bioontology.org/metadata/Ontology\",\"children\":\"http://www.w3.org/2002/07/owl#Class\",\"parents\":\"http://www.w3.org/2002/07/owl#Class\",\"descendants\":\"http://www.w3.org/2002/07/owl#Class\",\"ancestors\":\"http://www.w3.org/2002/07/owl#Class\",\"instances\":\"http://data.bioontology.org/metadata/Instance\",\"tree\":\"http://www.w3.org/2002/07/owl#Class\",\"notes\":\"http://data.bioontology.org/metadata/Note\",\"mappings\":\"http://data.bioontology.org/metadata/Mapping\",\"ui\":\"http://www.w3.org/2002/07/owl#Class\"}},\"@context\":{\"@vocab\":\"http://data.bioontology.org/metadata/\"}},\"hierarchy\":[],\"annotations\":[{\"from\":1,\"to\":6,\"matchType\":\"SYN\",\"text\":\"CANCER\"}],\"mappings\":[]},{\"annotatedClass\":{\"@id\":\"http://purl.lirmm.fr/ontology/MSHFRE/D009369\",\"@type\":\"http://www.w3.org/2002/07/owl#Class\",\"links\":{\"self\":\"http://data.bioportal.lirmm.fr/ontologies/MSHFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMSHFRE%2FD009369\",\"ontology\":\"http://data.bioportal.lirmm.fr/ontologies/MSHFRE\",\"children\":\"http://data.bioportal.lirmm.fr/ontologies/MSHFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMSHFRE%2FD009369/children\",\"parents\":\"http://data.bioportal.lirmm.fr/ontologies/MSHFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMSHFRE%2FD009369/parents\",\"descendants\":\"http://data.bioportal.lirmm.fr/ontologies/MSHFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMSHFRE%2FD009369/descendants\",\"ancestors\":\"http://data.bioportal.lirmm.fr/ontologies/MSHFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMSHFRE%2FD009369/ancestors\",\"instances\":\"http://data.bioportal.lirmm.fr/ontologies/MSHFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMSHFRE%2FD009369/instances\",\"tree\":\"http://data.bioportal.lirmm.fr/ontologies/MSHFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMSHFRE%2FD009369/tree\",\"notes\":\"http://data.bioportal.lirmm.fr/ontologies/MSHFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMSHFRE%2FD009369/notes\",\"mappings\":\"http://data.bioportal.lirmm.fr/ontologies/MSHFRE/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMSHFRE%2FD009369/mappings\",\"ui\":\"http://bioportal.lirmm.fr/ontologies/MSHFRE?p=classes&conceptid=http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMSHFRE%2FD009369\",\"@context\":{\"self\":\"http://www.w3.org/2002/07/owl#Class\",\"ontology\":\"http://data.bioontology.org/metadata/Ontology\",\"children\":\"http://www.w3.org/2002/07/owl#Class\",\"parents\":\"http://www.w3.org/2002/07/owl#Class\",\"descendants\":\"http://www.w3.org/2002/07/owl#Class\",\"ancestors\":\"http://www.w3.org/2002/07/owl#Class\",\"instances\":\"http://data.bioontology.org/metadata/Instance\",\"tree\":\"http://www.w3.org/2002/07/owl#Class\",\"notes\":\"http://data.bioontology.org/metadata/Note\",\"mappings\":\"http://data.bioontology.org/metadata/Mapping\",\"ui\":\"http://www.w3.org/2002/07/owl#Class\"}},\"@context\":{\"@vocab\":\"http://data.bioontology.org/metadata/\"}},\"hierarchy\":[],\"annotations\":[{\"from\":1,\"to\":6,\"matchType\":\"SYN\",\"text\":\"CANCER\"}],\"mappings\":[]},{\"annotatedClass\":{\"@id\":\"http://www.limics.fr/ontologies/ontolurgences#CancerParLocalisation\",\"@type\":\"http://www.w3.org/2002/07/owl#Class\",\"links\":{\"self\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23CancerParLocalisation\",\"ontology\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES\",\"children\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23CancerParLocalisation/children\",\"parents\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23CancerParLocalisation/parents\",\"descendants\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23CancerParLocalisation/descendants\",\"ancestors\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23CancerParLocalisation/ancestors\",\"instances\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23CancerParLocalisation/instances\",\"tree\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23CancerParLocalisation/tree\",\"notes\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23CancerParLocalisation/notes\",\"mappings\":\"http://data.bioportal.lirmm.fr/ontologies/ONTOLURGENCES/classes/http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23CancerParLocalisation/mappings\",\"ui\":\"http://bioportal.lirmm.fr/ontologies/ONTOLURGENCES?p=classes&conceptid=http%3A%2F%2Fwww.limics.fr%2Fontologies%2Fontolurgences%23CancerParLocalisation\",\"@context\":{\"self\":\"http://www.w3.org/2002/07/owl#Class\",\"ontology\":\"http://data.bioontology.org/metadata/Ontology\",\"children\":\"http://www.w3.org/2002/07/owl#Class\",\"parents\":\"http://www.w3.org/2002/07/owl#Class\",\"descendants\":\"http://www.w3.org/2002/07/owl#Class\",\"ancestors\":\"http://www.w3.org/2002/07/owl#Class\",\"instances\":\"http://data.bioontology.org/metadata/Instance\",\"tree\":\"http://www.w3.org/2002/07/owl#Class\",\"notes\":\"http://data.bioontology.org/metadata/Note\",\"mappings\":\"http://data.bioontology.org/metadata/Mapping\",\"ui\":\"http://www.w3.org/2002/07/owl#Class\"}},\"@context\":{\"@vocab\":\"http://data.bioontology.org/metadata/\"}},\"hierarchy\":[],\"annotations\":[{\"from\":1,\"to\":6,\"matchType\":\"PREF\",\"text\":\"CANCER\"}],\"mappings\":[]},{\"annotatedClass\":{\"@id\":\"http://chu-rouen.fr/cismef/SNOMED_int.#M-80003\",\"@type\":\"http://www.w3.org/2002/07/owl#Class\",\"links\":{\"self\":\"http://data.bioportal.lirmm.fr/ontologies/SNMIFRE/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FSNOMED_int.%23M-80003\",\"ontology\":\"http://data.bioportal.lirmm.fr/ontologies/SNMIFRE\",\"children\":\"http://data.bioportal.lirmm.fr/ontologies/SNMIFRE/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FSNOMED_int.%23M-80003/children\",\"parents\":\"http://data.bioportal.lirmm.fr/ontologies/SNMIFRE/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FSNOMED_int.%23M-80003/parents\",\"descendants\":\"http://data.bioportal.lirmm.fr/ontologies/SNMIFRE/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FSNOMED_int.%23M-80003/descendants\",\"ancestors\":\"http://data.bioportal.lirmm.fr/ontologies/SNMIFRE/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FSNOMED_int.%23M-80003/ancestors\",\"instances\":\"http://data.bioportal.lirmm.fr/ontologies/SNMIFRE/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FSNOMED_int.%23M-80003/instances\",\"tree\":\"http://data.bioportal.lirmm.fr/ontologies/SNMIFRE/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FSNOMED_int.%23M-80003/tree\",\"notes\":\"http://data.bioportal.lirmm.fr/ontologies/SNMIFRE/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FSNOMED_int.%23M-80003/notes\",\"mappings\":\"http://data.bioportal.lirmm.fr/ontologies/SNMIFRE/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FSNOMED_int.%23M-80003/mappings\",\"ui\":\"http://bioportal.lirmm.fr/ontologies/SNMIFRE?p=classes&conceptid=http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FSNOMED_int.%23M-80003\",\"@context\":{\"self\":\"http://www.w3.org/2002/07/owl#Class\",\"ontology\":\"http://data.bioontology.org/metadata/Ontology\",\"children\":\"http://www.w3.org/2002/07/owl#Class\",\"parents\":\"http://www.w3.org/2002/07/owl#Class\",\"descendants\":\"http://www.w3.org/2002/07/owl#Class\",\"ancestors\":\"http://www.w3.org/2002/07/owl#Class\",\"instances\":\"http://data.bioontology.org/metadata/Instance\",\"tree\":\"http://www.w3.org/2002/07/owl#Class\",\"notes\":\"http://data.bioontology.org/metadata/Note\",\"mappings\":\"http://data.bioontology.org/metadata/Mapping\",\"ui\":\"http://www.w3.org/2002/07/owl#Class\"}},\"@context\":{\"@vocab\":\"http://data.bioontology.org/metadata/\"}},\"hierarchy\":[],\"annotations\":[{\"from\":1,\"to\":6,\"matchType\":\"SYN\",\"text\":\"CANCER\"}],\"mappings\":[]},{\"annotatedClass\":{\"@id\":\"http://purl.lirmm.fr/ontology/MuEVo/vpm61\",\"@type\":\"http://www.w3.org/2002/07/owl#Class\",\"links\":{\"self\":\"http://data.bioportal.lirmm.fr/ontologies/MUEVO/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMuEVo%2Fvpm61\",\"ontology\":\"http://data.bioportal.lirmm.fr/ontologies/MUEVO\",\"children\":\"http://data.bioportal.lirmm.fr/ontologies/MUEVO/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMuEVo%2Fvpm61/children\",\"parents\":\"http://data.bioportal.lirmm.fr/ontologies/MUEVO/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMuEVo%2Fvpm61/parents\",\"descendants\":\"http://data.bioportal.lirmm.fr/ontologies/MUEVO/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMuEVo%2Fvpm61/descendants\",\"ancestors\":\"http://data.bioportal.lirmm.fr/ontologies/MUEVO/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMuEVo%2Fvpm61/ancestors\",\"instances\":\"http://data.bioportal.lirmm.fr/ontologies/MUEVO/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMuEVo%2Fvpm61/instances\",\"tree\":\"http://data.bioportal.lirmm.fr/ontologies/MUEVO/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMuEVo%2Fvpm61/tree\",\"notes\":\"http://data.bioportal.lirmm.fr/ontologies/MUEVO/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMuEVo%2Fvpm61/notes\",\"mappings\":\"http://data.bioportal.lirmm.fr/ontologies/MUEVO/classes/http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMuEVo%2Fvpm61/mappings\",\"ui\":\"http://bioportal.lirmm.fr/ontologies/MUEVO?p=classes&conceptid=http%3A%2F%2Fpurl.lirmm.fr%2Fontology%2FMuEVo%2Fvpm61\",\"@context\":{\"self\":\"http://www.w3.org/2002/07/owl#Class\",\"ontology\":\"http://data.bioontology.org/metadata/Ontology\",\"children\":\"http://www.w3.org/2002/07/owl#Class\",\"parents\":\"http://www.w3.org/2002/07/owl#Class\",\"descendants\":\"http://www.w3.org/2002/07/owl#Class\",\"ancestors\":\"http://www.w3.org/2002/07/owl#Class\",\"instances\":\"http://data.bioontology.org/metadata/Instance\",\"tree\":\"http://www.w3.org/2002/07/owl#Class\",\"notes\":\"http://data.bioontology.org/metadata/Note\",\"mappings\":\"http://data.bioontology.org/metadata/Mapping\",\"ui\":\"http://www.w3.org/2002/07/owl#Class\"}},\"@context\":{\"@vocab\":\"http://data.bioontology.org/metadata/\"}},\"hierarchy\":[],\"annotations\":[{\"from\":1,\"to\":6,\"matchType\":\"PREF\",\"text\":\"CANCER\"}],\"mappings\":[]},{\"annotatedClass\":{\"@id\":\"http://chu-rouen.fr/cismef/MedlinePlus#T25\",\"@type\":\"http://www.w3.org/2002/07/owl#Class\",\"links\":{\"self\":\"http://data.bioportal.lirmm.fr/ontologies/MEDLINEPLUS/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FMedlinePlus%23T25\",\"ontology\":\"http://data.bioportal.lirmm.fr/ontologies/MEDLINEPLUS\",\"children\":\"http://data.bioportal.lirmm.fr/ontologies/MEDLINEPLUS/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FMedlinePlus%23T25/children\",\"parents\":\"http://data.bioportal.lirmm.fr/ontologies/MEDLINEPLUS/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FMedlinePlus%23T25/parents\",\"descendants\":\"http://data.bioportal.lirmm.fr/ontologies/MEDLINEPLUS/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FMedlinePlus%23T25/descendants\",\"ancestors\":\"http://data.bioportal.lirmm.fr/ontologies/MEDLINEPLUS/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FMedlinePlus%23T25/ancestors\",\"instances\":\"http://data.bioportal.lirmm.fr/ontologies/MEDLINEPLUS/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FMedlinePlus%23T25/instances\",\"tree\":\"http://data.bioportal.lirmm.fr/ontologies/MEDLINEPLUS/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FMedlinePlus%23T25/tree\",\"notes\":\"http://data.bioportal.lirmm.fr/ontologies/MEDLINEPLUS/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FMedlinePlus%23T25/notes\",\"mappings\":\"http://data.bioportal.lirmm.fr/ontologies/MEDLINEPLUS/classes/http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FMedlinePlus%23T25/mappings\",\"ui\":\"http://bioportal.lirmm.fr/ontologies/MEDLINEPLUS?p=classes&conceptid=http%3A%2F%2Fchu-rouen.fr%2Fcismef%2FMedlinePlus%23T25\",\"@context\":{\"self\":\"http://www.w3.org/2002/07/owl#Class\",\"ontology\":\"http://data.bioontology.org/metadata/Ontology\",\"children\":\"http://www.w3.org/2002/07/owl#Class\",\"parents\":\"http://www.w3.org/2002/07/owl#Class\",\"descendants\":\"http://www.w3.org/2002/07/owl#Class\",\"ancestors\":\"http://www.w3.org/2002/07/owl#Class\",\"instances\":\"http://data.bioontology.org/metadata/Instance\",\"tree\":\"http://www.w3.org/2002/07/owl#Class\",\"notes\":\"http://data.bioontology.org/metadata/Note\",\"mappings\":\"http://data.bioontology.org/metadata/Mapping\",\"ui\":\"http://www.w3.org/2002/07/owl#Class\"}},\"@context\":{\"@vocab\":\"http://data.bioontology.org/metadata/\"}},\"hierarchy\":[],\"annotations\":[{\"from\":1,\"to\":6,\"matchType\":\"PREF\",\"text\":\"CANCER\"}],\"mappings\":[]}]";
    private List<Annotation> annotationList;
    private BioPortalJSONAnnotationParser parser;

    @Before
    public void setUp() throws Exception {
        Store store = new JenaRemoteSPARQLStore("http://sparql.bioportal.lirmm.fr/sparql/");
        StoreHandler.registerStoreInstance(store);
        PropertyRetriever cuiRetrieval = new CUIPropertyRetriever();
        PropertyRetriever typeRetrieval = new SemanticTypePropertyRetriever();
        UMLSGroupIndex umlsGroupIndex = UMLSSemanticGroupsLoader.load();
        AnnotationFactory annotationFactory = new BioPortalLazyAnnotationFactory();

        this.parser = new BioPortalJSONAnnotationParser(annotationFactory, cuiRetrieval, typeRetrieval, umlsGroupIndex);
        this.annotationList = parser.parseAnnotations(jsonOutput);

    }

    @Test
    public void testLazyJSONGeneration() throws Exception {
        LIRMMOutputGeneratorDispatcher dispatcher = new LIRMMOutputGeneratorDispatcher();
        AnnotatorOutput output = dispatcher.generate(annotationList, "http://bioportal.lirmm.fr:8080/servlet?");
        logger.info(output.getContent());
        List<Annotation> returnList = parser.parseAnnotations(output.getContent());
        assert returnList.size() == annotationList.size();

    }

    @Test
    public void testJenaGeneration() throws Exception {
        LIRMMOutputGeneratorDispatcher dispatcher = new LIRMMOutputGeneratorDispatcher();
        AnnotatorOutput output = dispatcher.generate(annotationList, "http://bioportal.lirmm.fr:8080/servlet?");
        logger.info(output.getContent());
    }

    @Test
    public void testLazyBRATGeneration() throws Exception {
        LIRMMOutputGeneratorDispatcher dispatcher = new LIRMMOutputGeneratorDispatcher();
        AnnotatorOutput output = dispatcher.generate(annotationList, "http://bioportal.lirmm.fr:8080/servlet?");
        logger.info(output.getContent());
    }
}
