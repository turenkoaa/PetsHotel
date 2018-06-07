
const baseUrl = 'http://localhost:8200/';

export function createEnv() {
    return {
        getUserByEmail: baseUrl + 'user/by-email/',
        getPetsByOwner: baseUrl + 'pet/all-by-owner/',
        getRequestsAllByAuthor: baseUrl + 'request/all-by-author/',
        getResponsesAllByAuthor: baseUrl + 'response/all-by-author/',
        getNewRequests: baseUrl + 'request/all-new/',
        getResponsesByRequset: baseUrl + 'response/all-by-request/',
        getUsersToBlock: baseUrl + 'admin/users-to-block',
        getDislikeForUser: baseUrl + 'review/save',
        getBlockuser: baseUrl + 'admin/block-user/',
        getRequset: baseUrl + 'request/',
        getCreateResponse: baseUrl + 'response/create',
        getUser: baseUrl + 'user/',
        getAllUsers: baseUrl + 'user/all',
        getCreateRequest: baseUrl + 'request/create',
    };
}

export let environment = createEnv();
