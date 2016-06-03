package NormGenerator;

import analisador.lexico.TokenMark;
import com.google.common.collect.Multimap;
import com.norm.checker.norm.definition.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static analisador.lexico.Token.*;

/**
 * Generate the norm as specified in MultiConflicts
 * Created by Luccas Oliveira on 04/04/2016.
 */
public class Generator {
    private Long code;
    private DeonticConcept deonticConcept;
    private Context context;
    private Entity entity;
    private BehaviorMultipleParameters behaviorMultipleParameters;
    private Constraint activationConstraint = null;
    private Constraint deactivationConstraint = null;


    public void setCode(Long cd) {
        this.code = cd;
    }

    /**
     * Translate lexical token into DeonticConcept
     * @param tokenConcept
     * @throws InvalidTokenException when a bad token is passed
     */
    public void setDeonticConcept(TokenMark tokenConcept) throws InvalidTokenException {
        if(tokenConcept == TOKEN_OBLIGED) {
            this.deonticConcept = DeonticConcept.OBLIGATION;
        }
        else if(tokenConcept == TOKEN_PERMISSION) {
            this.deonticConcept = DeonticConcept.PERMISSION;
        }
        else if(tokenConcept == TOKEN_FORBIDDEN) {
            this.deonticConcept = DeonticConcept.PROHIBITION;
        }
        else {
            throw new InvalidTokenException(tokenConcept);
        }
    }

    /**
     * Translate lexical token into Context, without a name. By default name is "default"
     * @param tokenContext lexical token
     * @throws InvalidTokenException when a bad token is passed
     */
    public void setContext(TokenMark tokenContext) throws InvalidTokenException {
        if(tokenContext == TOKEN_ORGANIZATION) {
            this.context = new Context("default", ContextType.ORGANIZATION);
        }
        else if(tokenContext == TOKEN_ENVIRONMENT) {
            this.context = new Context("default", ContextType.ENVIRONMENT);
        }
        else {
            throw new InvalidTokenException(tokenContext);
        }
    }

    /**
     * Translate lexical token into Context, defining a name
     * @param tokenContext lexical token
     * @param name context name
     * @throws InvalidTokenException when a bad token is passed
     */
    public void setContext(TokenMark tokenContext, String name) throws  InvalidTokenException {
        if (tokenContext == TOKEN_ORGANIZATION) {
            this.context = new Context(name, ContextType.ORGANIZATION);
        } else if (tokenContext == TOKEN_ENVIRONMENT) {
            this.context = new Context(name, ContextType.ENVIRONMENT);
        } else {
            throw new InvalidTokenException(tokenContext);
        }

    }

    /**
     * Translate lexical token into Entity, without a name, by default name is "default_name"
     * @param tokenEntity lexical token.
     * @throws InvalidTokenException when a bad token is passed.
     */
    public void setEntity(TokenMark tokenEntity) throws InvalidTokenException {
        if(tokenEntity == TOKEN_AGENT) {
            this.entity = new Entity("default_name", EntityType.AGENT);
        }
        else if(tokenEntity == TOKEN_ROLE) {
            this.entity = new Entity("default_name", EntityType.ROLE);
        }
        else if(tokenEntity == TOKEN_ORGANIZATION) {
            this.entity = new Entity("default_name", EntityType.ORGANIZATION);
        }
        else if(tokenEntity == TOKEN_ALL) {
            this.entity = new Entity("default_name", EntityType.ALL);
        }
        else {
            throw new InvalidTokenException(tokenEntity);
        }
    }

    /**
     * Translate lexical token into Entity, defining a name.
     * @param name Entity name
     * @param tokenEntity lexial token
     * @throws InvalidTokenException when a bad token is passed
     */
    public void setEntity(String name, TokenMark tokenEntity) throws InvalidTokenException {
        if(tokenEntity == TOKEN_AGENT) {
            this.entity = new Entity(name, EntityType.AGENT);
        }
        else if(tokenEntity == TOKEN_ROLE) {
            this.entity = new Entity(name, EntityType.ROLE);
        }
        else if(tokenEntity == TOKEN_ORGANIZATION) {
            this.entity = new Entity(name, EntityType.ORGANIZATION);
        }
        else if(tokenEntity == TOKEN_ALL) {
            this.entity = new Entity(name, EntityType.ALL);
        }
        else {
            throw new InvalidTokenException(tokenEntity);
        }
    }
    /*TODO: Implement it*/
    public void setActivationConstraint(LocalDate activationDate) {
        activationConstraint = new ConstraintDate(ConstraintType.DATETYPE, activationDate);
    }
    /*TODO: Implement it*/
    public void setDeactivationConstraint(LocalDate deactivationDate) {
        deactivationConstraint= new ConstraintDate(ConstraintType.DATETYPE, deactivationDate);
    }

    public void setBehaviorMultipleParameters(Multimap<String,String> behavior) {
        /*Current Mapping:
        * _action = BehaviorMultipleParameters.name
        * _object = BehaviorMultiParameters.object
        * The rest is transformed into a Map<String, Set<String> where key is an attribute,
        * and values belong to a set.
        */
        Set<String> keys = behavior.keySet();
        behaviorMultipleParameters = new BehaviorMultipleParameters(""); /*Create with blank string*/

        for(String key: keys) {
            /*Iterate trough Multimap*/
            Set<String> values;
            values = (Set<String>) behavior.get(key);
            behaviorMultipleParameters.addSetOfElements(key,values);
        }
    }

    public Norm generateNorm(String actionName, String objectName) {
        /*TODO: Verify all fields*/
        this.behaviorMultipleParameters.setName(actionName);
        this.behaviorMultipleParameters.setObject(objectName);
        return new Norm(this.code,this.deonticConcept,this.context,this.entity,this.behaviorMultipleParameters,
                this.activationConstraint,this.deactivationConstraint);
    }


}
