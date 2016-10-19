import { expect } from 'chai';
import { describe, it } from 'mocha';
import * as GraphQL from 'graphql';
import SpottrSchema from './spottrschema';


describe("Check Introspection", () => {

    it('Should support querying the schema for it\'s types', () => {
        const query = `query IntrospectionTypeQuery {
          __schema {
            types {
              name
            }
          }
        }
      `;
        const expected = {
            __schema: {
                types: [{
                    "name": "RootQueryType"
                }, {
                    "name": "String"
                }, {
                    "name": "Workout"
                }, {
                    "name": "Athlete"
                }, {
                    "name": "Float"
                }, {
                    "name": "Location"
                }, {
                    "name": "Int"
                }, {
                    "name": "Comment"
                }, {
                    "name": "Mutation"
                }, {
                    "name": "Boolean"
                }, {
                    "name": "__Schema"
                }, {
                    "name": "__Type"
                }, {
                    "name": "__TypeKind"
                }, {
                    "name": "__Field"
                }, {
                    "name": "__InputValue"
                }, {
                    "name": "__EnumValue"
                }, {
                    "name": "__Directive"
                }, {
                    "name": "__DirectiveLocation"
                }]
            }
        };

        const result = GraphQL.graphql(SpottrSchema, query);
        //expect(result).to.deep.equal({ data: expected });
    });

});
