function getFinancialInstitutionPage(params) {
    return $axios({
        url: '/financialInstitution/page',
        method: 'get',
        params,
    });
}

function getFinancialInstitutionList() {
    return $axios({
        url: '/financialInstitution/list',
        method: 'get',
    });
}