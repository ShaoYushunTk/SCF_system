function getFundFlowPage(params) {
    return $axios({
        url: '/fundFlow/page',
        method: 'get',
        params,
    });
}

function getFundFlowByCompanyId(id, params) {
    return $axios({
        url: `/fundFlow/company/${id}`,
        method: 'get',
        params,
    });
}