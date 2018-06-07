import {User} from './user';
import {Request} from './request';

export interface Response {
    id: number;
    user: User;
    request: Request;
    status: string;
    details: string;
    cost: number;
}
