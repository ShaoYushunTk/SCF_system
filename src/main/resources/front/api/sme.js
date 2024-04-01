function getSMEPage(params) {
    return $axios({
        url: '/smallMiddleEnterprise/page',
        method: 'get',
        params,
    });
}