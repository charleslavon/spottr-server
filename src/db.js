import Sequelize from 'sequelize';
import _ from 'lodash';
import Faker from 'faker';


const Conn = new Sequelize(process.env.DATABASE_URL);

const Workout = Conn.define('workout', {
    id: {
        type: Sequelize.DataTypes.UUID,
        defaultValue: Sequelize.DataTypes.UUIDV4,
        primaryKey: true
    },
    date: {
        type: Sequelize.DATE,
        defaultValue: Sequelize.NOW
    },
    description: { type: Sequelize.STRING },
    likes: {
        type: Sequelize.INTEGER,
        defaultValue: 0
    }
});

const Comment = Conn.define('comment', {
    id: {
        type: Sequelize.DataTypes.UUID,
        defaultValue: Sequelize.DataTypes.UUIDV4,
        primaryKey: true
    },
    comment: { type: Sequelize.STRING, allowNull: false }
});

const Athlete = Conn.define('athlete', {
    id: {
        type: Sequelize.DataTypes.UUID,
        defaultValue: Sequelize.DataTypes.UUIDV4,
        primaryKey: true
    },
    name: { type: Sequelize.STRING, allowNull: false },
    email: {
        type: Sequelize.STRING,
        allowNull: false,
        unique: true,
        validate: { isEmail: true }
    },
    latitude: {
        type: Sequelize.DECIMAL,
        allowNull: true,
        defaultValue: null,
        validate: { min: -90, max: 90 }
    },
    longitude: {
        type: Sequelize.DECIMAL,
        allowNull: true,
        defaultValue: null,
        validate: { min: -180, max: 180 }
    }
});

const Location = Conn.define('location', {
    id: {
        type: Sequelize.DataTypes.UUID,
        defaultValue: Sequelize.DataTypes.UUIDV4,
        primaryKey: true
    },
    name: { type: Sequelize.STRING, allowNull: false },
    streetAddress: { type: Sequelize.STRING },
    description: { type: Sequelize.STRING },
    zipCode: { type: Sequelize.STRING },
    city: { type: Sequelize.STRING },
    latitude: {
        type: Sequelize.DECIMAL,
        allowNull: true,
        defaultValue: null,
        validate: { min: -90, max: 90 }
    },
    longitude: {
        type: Sequelize.DECIMAL,
        allowNull: true,
        defaultValue: null,
        validate: { min: -180, max: 180 }
    },
});


//Each Workout has one location
Location.hasOne(Workout, {
    as: 'location',
    foreignKey: 'location_id'
});

//Workout has one author of the Athlete model
Athlete.hasMany(Workout, {
    as: 'author',
    foreignKey: 'author_id'
});

//A workout may have many comments
Workout.hasMany(Comment, {
    as: 'comments',
    foreignKey: 'workout_id'
});

//An Athlete may have authored many comments
Athlete.hasMany(Comment, {
    as: 'author',
    foreignKey: 'author_id'
});
Comment.belongsTo(Athlete, { as: 'author', foreignKey: 'author_id' });

//A workout may be attended by many athletes
const AthleteWorkouts = Conn.define('athlete_workout', {
    is_done: Sequelize.BOOLEAN
}, {
    timestamps: false
});
Athlete.belongsToMany(Workout, { through: AthleteWorkouts, foreignKey: 'athlete_id', as: 'workouts' });
Workout.belongsToMany(Athlete, { through: AthleteWorkouts, foreignKey: 'workout_id', as: 'attendees' });



Conn.sync({ force: true }).then(() => {

    _.times(10, () => {
        return Athlete.create({
            name: Faker.name.firstName(),
            email: Faker.internet.email()
        });
    });

    _.times(10, () => {
        return Workout.create({
            description: Faker.lorem.paragraph()
        });
    });

    _.times(10, () => {
        return Comment.create({
            comment: Faker.lorem.sentence()
        });
    });

    _.times(10, () => {
        return Location.create({
            name: Faker.company.companyName(),
            description: Faker.internet.domainName(),
            streetAddress: Faker.address.streetAddress(),
            city: Faker.address.city(),
            zipCode: Faker.address.zipCode(),
            latitude: Faker.address.latitude(),
            longitude: Faker.address.longitude()
        });
    });
});



export default Conn;
