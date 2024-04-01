function getFinancialInstitutionPage(params) {
    return $axios({
        url: '/financialInstitution/page',
        method: 'get',
        params,
    });
}