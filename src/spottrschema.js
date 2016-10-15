import {
    GraphQLInt,
    GraphQLObjectType,
    GraphQLList,
    GraphQLNonNull,
    GraphQLSchema,
    GraphQLString,
    GraphQLFloat,
    GraphQLBoolean
} from 'graphql/type';

import Db from './db';

/*
    type Workout {
        id: String
        author: String
        date: String
        location: Location
        description: String
        likes: Number
        comments: [Comment]
        attendees: [Athlete]
    }
*/
const WorkoutType = new GraphQLObjectType({
    name: 'Workout',
    description: 'Just move - any fitness or outdoors activity',
    fields: () => ({
        id: {
            type: GraphQLString,
            description: 'The id of the workout.',
            resolve: workout => workout.id,
        },
        author: {
            type: AthleteType,
            description: 'The author of the workout.',
            resolve: workout => {
                return Db.models.athlete.find({ where: { id: workout.author_id } });
            }
        },
        date: {
            type: GraphQLString,
            description: 'The date of the workout.',
            resolve: workout => workout.date,
        },
        location: {
            type: LocationType,
            description: 'The location of the workout.',
            resolve: workout => {
                return Db.models.location.find({ where: { id: workout.location_id } });
            }
        },
        description: {
            type: GraphQLString,
            description: 'The description of the workout.',
            resolve: workout => workout.description,
        },
        likes: {
            type: GraphQLInt,
            description: 'The number of people interested in the workout',
            resolve: workout => workout.likes,
        },
        comments: {
            type: new GraphQLList(CommentType),
            description: 'The conversations about this workout ' +
                'or an empty list',
            resolve: workout => {
                return Db.models.comment.findAll({ where: { workout_id: workout.id } });
            }
        },
        attendees: {
            type: new GraphQLList(AthleteType),
            description: 'The athletes who will join the author in the workout' +
                'or an empty list',
            resolve: workout => workout.getAttendees(),
        }

    }),
    interfaces: []
});


/*
    type Athlete {
        id: String
        name: String
        email: String
    }
*/
const AthleteType = new GraphQLObjectType({
    name: 'Athlete',
    description: 'A spottr user',
    fields: () => ({
        id: {
            type: GraphQLString,
            description: 'The id of the athlete.',
            resolve: athlete => athlete.id
        },
        name: {
            type: GraphQLString,
            description: 'The name of the athlete.',
            resolve: athlete => athlete.name
        },
        email: {
            type: GraphQLString,
            description: 'The email of the athlete.',
            resolve: athlete => athlete.email
        },
        latitude: {
            type: GraphQLFloat,
            description: 'The athlete\'s last known location - latitude.',
            resolve: athlete => athlete.latitude,
        },
        longitude: {
            type: GraphQLFloat,
            description: 'The athlete\'s last known location - longitude.',
            resolve: athlete => athlete.longitude,
        },
    }),
    interfaces: []
});


/*
    type Comment {
        id: String
        author: String
        comment: String
    }

*/
const CommentType = new GraphQLObjectType({
    name: 'Comment',
    description: 'A text/emoji/gif message',
    fields: () => ({
        id: {
            type: GraphQLString,
            description: 'The id of the comment.',
            resolve: comment => comment.id
        },
        author: {
            type: AthleteType,
            description: 'The author of the comment.',
            resolve: comment => comment.getAuthor(),
        },
        comment: {
            type: GraphQLString,
            description: 'The message content.',
            resolve: comment => comment.comment,
        }
    }),
    interfaces: []
});


/*
    type Location {
        id: String
        name: String
        description: String
        latitude: String
        longitude: String
    }
*/
const LocationType = new GraphQLObjectType({
    name: 'Location',
    description: 'Geographical location of the fitness activity',
    fields: () => ({
        id: {
            type: GraphQLString,
            description: 'The id of the location.',
            resolve: location => location.id,
        },
        name: {
            type: GraphQLString,
            description: 'The name of the location.',
            resolve: location => location.name,
        },
        description: {
            type: GraphQLString,
            resolve: location => location.description,
        },
        website: {
            type: GraphQLString,
            resolve: location => location.website,
        },
        streetAddress: {
            type: GraphQLString,
            resolve: location => location.streetAddress,
        },
        zipCode: {
            type: GraphQLString,
            resolve: location => location.zipCode,
        },
        city: {
            type: GraphQLString,
            resolve: location => location.city,
        },
        latitude: {
            type: GraphQLFloat,
            description: 'The global positioning latitude of the location.',
            resolve: location => location.latitude,
        },
        longitude: {
            type: GraphQLFloat,
            description: 'The global positioning longitude of the location.',
            resolve: location => location.longitude,
        },
    }),
    interfaces: []
});


/*

This type is the root entry point for all queries.

*/
const QueryType = new GraphQLObjectType({
    name: 'RootQueryType',
    fields: () => ({
        workouts: {
            type: new GraphQLList(WorkoutType),
            description: 'All the workouts!',
            args: {
                id: {
                    type: GraphQLString
                },
                location_id: {
                    type: GraphQLString
                },
                author_id: {
                    type: GraphQLString
                }
            },
            resolve: (root, args) => {
                return Db.models.workout.findAll({ where: args });
            },
        },
        athletes: {
            type: new GraphQLList(AthleteType),
            description: 'Get all the athletes!',
            args: {
                id: {
                    type: GraphQLString
                },
                email: {
                    type: GraphQLString
                },
                name: {
                    type: GraphQLString
                }
            },
            resolve: (root, args) => {
                return Db.models.athlete.findAll({ where: args });
            },
        },
        locations: {
            type: new GraphQLList(LocationType),
            description: 'All the Locations!',
            args: {
                id: {
                    type: GraphQLString
                },
                name: {
                    type: GraphQLString
                },
                city: {
                    type: GraphQLString
                }

            },
            resolve: (root, args) => {
                return Db.models.location.findAll({ where: args });
            },
        }
    })
});


/*
    type Mutation ( queries that modify data)

        athlete(name: String, email: String): Athlete

        attendee(id: String): Athlete

        comment(author: String, comment: String, id: string): Workout

        addLike(id: String, num: Number)

        addLocation(name: String, latitude: Number, longitude: Number, description: String)

        addWorkout(author: String, date: String, location: id, description: String))

*/
//TODO think about adding error handling to all promise callbacks
const AddAthlete = {
    name: 'AddAthlete',
    type: AthleteType,
    description: 'Adds a new Athlete (user)',
    args: {
        name: {
            type: new GraphQLNonNull(GraphQLString),
            description: 'The athlete\'s name'
        },
        email: {
            type: new GraphQLNonNull(GraphQLString),
            description: 'The athlete\'s email'
        },
        latitude: {
            type: GraphQLFloat,
            description: 'The athlete\'s last known location - latitude.',
        },
        longitude: {
            type: GraphQLFloat,
            description: 'The athlete\'s last known location - longitude.',
        }
    },
    resolve: (_, args) => {
        return Db.models.athlete.create(args);
    }
};


const AddWorkoutAttendee = {
    name: 'AddWorkoutAttendee',
    type: WorkoutType,
    description: 'Add an athlete as attendee to a workout',
    args: {
        workoutId: {
            type: new GraphQLNonNull(GraphQLString),
            description: 'Workout Id'
        },
        athleteId: {
            type: new GraphQLNonNull(GraphQLString),
            description: 'Athlete Id'
        }
    },
    resolve: (_, args) => {
        return Db.models.athlete_workout.create({
            workout_id: args.workoutId,
            athlete_id: args.athleteId
        }).
        then(result => {
            return Db.models.workout.findById(result.workout_id);
        });
    }
};

const AddComment = {
    name: 'AddComment',
    type: CommentType,
    description: 'Add a comment to an existing Workout',
    args: {
        workoutId: {
            type: new GraphQLNonNull(GraphQLString),
            description: 'Workout Id'
        },
        authorId: {
            type: new GraphQLNonNull(GraphQLString),
            description: 'Author Id'
        },
        comment: {
            type: new GraphQLNonNull(GraphQLString),
            description: 'The comment text'
        }
    },
    resolve: (_, args) => {
        return Db.models.comment.create({
            workout_id: args.workoutId,
            author_id: args.authorId,
            comment: args.comment
        });
    }
};

const AddLikes = {
    name: 'AddLikes',
    type: WorkoutType,
    description: 'Increment the workout\'s like count by the provided amount',
    args: {
        workoutId: {
            type: new GraphQLNonNull(GraphQLString),
            description: 'Workout Id'
        },
        num: {
            type: GraphQLInt,
            description: 'Increment the number of likes by this amount'
        }
    },
    resolve: (_, args) => {
        let val = Db.models.workout.findById(args.id).then(workout => {
            return workout.update({
                likes: workout.likes + args.num
            });
        });

        return val;
    }
};


const AddLocation = {
    name: 'LocationMutation',
    type: LocationType,
    description: 'Add a location',
    args: {
        name: {
            type: new GraphQLNonNull(GraphQLString)
        },
        streetAddress: {
            type: GraphQLString
        },
        description: {
            type: GraphQLString
        },
        zipCode: {
            type: GraphQLString
        },
        city: {
            type: GraphQLString
        },
        latitude: {
            type: GraphQLFloat
        },
        longitude: {
            type: GraphQLFloat
        },
        force: {
            type: GraphQLBoolean
        }
    },
    resolve: (_, args) => {
        let matches = {};
        Db.models.location.findAndCountAll({
            where: {
                name: {
                    $like: args.name
                }
            },
            limit: 20
        }).then(result => {
            matches.count = result.count;
            matches.rows = result.rows;
        });
        if (!args.force || matches.count === 0)
            return Db.models.location.create({
                name: args.name,
                streetAddress: args.streetAddress,
                description: args.description,
                zipCode: args.zipCode,
                city: args.city,
                latitude: args.latitude,
                longitude: args.longitude
            });
    }
};


const AddWorkout = {
    name: 'WorkoutMutation',
    type: WorkoutType,
    description: 'Add a workout',
    args: {
        authorId: {
            type: new GraphQLNonNull(GraphQLString)
        },
        date: {
            type: new GraphQLNonNull(GraphQLString)
        },
        locationId: {
            type: new GraphQLNonNull(GraphQLString)
        },
        description: {
            type: new GraphQLNonNull(GraphQLString)
        }
    },
    resolve: (_, args) => {
        return Db.models.workout.create({
            date: args.date,
            description: args.description,
            location_id: args.locationId,
            author_id: args.authorId
        });
    }
};


const MutationType = new GraphQLObjectType({
    name: 'Mutation',
    fields: () => ({
        addWorkout: AddWorkout,
        addLocation: AddLocation,
        addLike: AddLikes,
        addComment: AddComment,
        addWorkoutAttendee: AddWorkoutAttendee,
        addAthlete: AddAthlete
    })

});



//Construct and export the final schema.
const SpottrSchema = new GraphQLSchema({
    query: QueryType,
    mutation: MutationType,
    types: [WorkoutType, AthleteType, CommentType, LocationType]
});

export default SpottrSchema;
