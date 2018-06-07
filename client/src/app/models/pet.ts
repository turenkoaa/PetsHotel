import {User} from './user';

export interface Pet {
    id: number;
    owner: User;
    petType: string;
    name: string;
    age: number;
    passport: string;
}
