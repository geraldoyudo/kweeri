package com.geraldoyudo.kweeri.core.boolresolver;

import java.util.LinkedList;
import java.util.List;

public class BooleanResolver implements BoolResolver{
    private static BooleanResolver resolver;

    private List<BoolResolver> resolvers = new LinkedList<>();

    private BooleanResolver(){
        resolvers.add(new BooleanBoolResolver());
        resolvers.add(new NumberBoolResolver());
        resolvers.add(new StringBoolResolver());
        resolvers.add(new ObjectBoolResolver());
    }

    @Override
    public boolean resolve(Object object) {
        for (BoolResolver boolResolver: resolvers){
            if(boolResolver.canResolve(object)){
                return boolResolver.resolve(object);
            }
        }
        return false;
    }

    @Override
    public boolean canResolve(Object object) {
        return true;
    }

    public static BooleanResolver instance(){
        if(resolver == null){
            resolver = new BooleanResolver();
        }
        return resolver;
    }
}
