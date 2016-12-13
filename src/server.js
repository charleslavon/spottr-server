import Express from 'express';
//import GraphqlHTTP from 'express-graphql';
import SpottrSchema from './spottrschema';
import BodyParser from 'body-parser';
import * as GraphQL from 'graphql';

const app = Express();
const PORT = process.env.PORT;

/*
app.use('/spottrql', GraphqlHTTP({
    schema: SpottrSchema,
    pretty: true,
    graphiql: true
})); */


// use body-parser so we can grab the incoming graphql query data
app.use(BodyParser.text({ type: 'application/graphql' }));

app.post('/spottrql', (req, res) => {

    GraphQL.graphql(SpottrSchema, req.body)
        .then((result) => {
            res.send(JSON.stringify(result, null, 2));
        });
});

app.options('/spottrql', (req, res) => {
  res.header('Access-Control-Allow-Headers', 'Origin, Content-Type, Accept, Access-Control-Allow-Origin');
  res.header('Access-Control-Allow-Origin', 'http://clg.local.com:8080, http://app.spottr.fit');
  res.header('Access-Control-Allow-Methods', 'OPTIONS, POST');
  res.send();
});

let server = app.listen(PORT, () => {
    let host = server.address().address;
    let port = server.address().port;

    console.log(`spottr-server listening at http://${host}/${port}`);
});
