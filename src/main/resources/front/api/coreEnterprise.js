function getCoreEnterprisePage(params) {
    return $axios({
        url: '/coreEnterprise/page',
        method: 'get',
        params,
    });
}

function getCoreEnterpriseCreditRatingPage(params) {
    return $axios({
        url: '/coreEnterprise/creditRatingPage',
        method: 'get',
        params,
    });
}

function saveCoreEnterpriseCreditRating(id, params) {
    return $axios({
        url: `/coreEnterprise/${id}/saveCreditRating`,
        method: 'post',
        params,
    });
}