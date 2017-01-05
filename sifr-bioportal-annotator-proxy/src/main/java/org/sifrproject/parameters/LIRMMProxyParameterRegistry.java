package org.sifrproject.parameters;


import org.sifrproject.parameters.api.ParameterHandler;
import org.sifrproject.parameters.api.ParameterRegistry;
import org.sifrproject.parameters.exceptions.InvalidParameterException;
import org.sifrproject.postannotation.api.PostAnnotationRegistry;
import org.sifrproject.util.UrlParameters;

import java.util.*;

public class LIRMMProxyParameterRegistry implements ParameterRegistry {


    private final List<Parameters> parameterss;
    private final Map<Parameters, ParameterHandler> parameterHandlers;

    private PostAnnotationRegistry postAnnotationRegistry;

    public LIRMMProxyParameterRegistry(PostAnnotationRegistry postAnnotationRegistry) {
        parameterss = new ArrayList<>();
        parameterHandlers = new HashMap<>();
        this.postAnnotationRegistry = postAnnotationRegistry;
    }

    @Override
    public void registerParameterHandler(String name, ParameterHandler parameterHandler, boolean isOptional) {
        Parameters currentParameters = new Parameters(name, isOptional);
        parameterss.add(currentParameters);
        parameterHandlers.put(currentParameters, parameterHandler);
    }


    @Override
    public final void processParameters(UrlParameters urlParameters) throws InvalidParameterException {
        if (!urlParameters.containsKey("text")) {
            throw new InvalidParameterException("Mandatory parameter 'text' missing");
        }

        for (Parameters parameter : this.parameterss) {
            if (!parameter.isOptional() && !urlParameters.containsKey(parameter.getName())) {
                throw new InvalidParameterException(String.format("Mandatory parameter missing -- %s", parameter.getName()));
            } else if (parameter.atLeastOneContained(urlParameters)) {
                parameterHandlers.get(parameter).processParameter(urlParameters, postAnnotationRegistry);
            }
        }
    }

    private final class Parameters {
        private List<String> names;
        private boolean isOptional;

        Parameters(String names, boolean isOptional) {
            this.names = new ArrayList<>();
            if (names.contains("|")) {
                Collections.addAll(this.names, names.split("\\|"));
            } else {
                this.names.add(names);
            }
            this.isOptional = isOptional;
        }

        String getName() {
            return names.get(0);
        }

        List<String> getNames() {
            return names;
        }

        boolean atLeastOneContained(UrlParameters parameters) {
            boolean atLeastOne = false;
            for (String name : names) {
                atLeastOne = parameters.containsKey(name);
                if (atLeastOne) break;
            }
            return atLeastOne;
        }

        boolean isOptional() {
            return isOptional;
        }
    }
}