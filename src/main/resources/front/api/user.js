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