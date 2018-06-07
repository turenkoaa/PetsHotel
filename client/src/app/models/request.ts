import {Pet} from './pet';
import {User} from './user';

export interface Request {
    id: number;
    user: User;
    startDate: string;
    endDate: string;
    status: string;
    pets: Pet[];
    cost: number;
}
