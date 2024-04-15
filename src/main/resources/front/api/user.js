function getUserPage(params) {
    return $axios({
        url: '/user/page',
        method: 'get',
        params,
    });
}

function updateUserStatus(id, params) {
    return $axios({
        url: `/user/${id}/updateStatus`,
        method: 'post',
        params,
    });
}

function getUserById(id) {
    return $axios({
        url: `/user/${id}`,
        method: 'get',
    });
}

function updateUserById(id, params) {
    return $axios({
        url: `/user/${id}/update`,
        method: 'post',
        data: params
    });
}

function createUser(params) {
    return $axios({
        url: '/user/save',
        method: 'post',
        data: params
    });
}