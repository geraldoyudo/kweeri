package com.geraldoyudo.kweeri.mongo;

import com.geraldoyudo.kweeri.core.converters.QueryConverter;
import com.geraldoyudo.kweeri.core.expression.Expression;

/*
db.inventory.find( { status: "D" } )
db.inventory.find( { status: { $in: [ "A", "D" ] } } )
db.inventory.find( { status: "A", qty: { $lt: 30 } } )
db.inventory.find( { $or: [ { status: "A" }, { qty: { $lt: 30 } } ] } )
db.inventory.find( {
     status: "A",
     $or: [ { qty: { $lt: 30 } }, { item: /^p/ } ]
} )
 */
public class MongoQueryConverter implements QueryConverter<String> {
    public String convertExpressionToQuery(Expression expression) {
        return null;
    }
}
