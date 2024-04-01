function deleteOrdersByCompanyId(companyId) {
    return $axios({
        url: `/order/${companyId}/delete`,
        method: 'post',
    });
}

function getOrdersPage(params) {
    return $axios({
        url: '/order/page',
        method: 'get',
        params,
    });
}

function deleteOrdersById(id) {
    return $axios({
        url: `/order/${id}/delete`,
        method: 'post',
    });
}

function getOrdersByCompanyId(id, params) {
    return $axios({
        url: `/order/company/${id}`,
        method: 'get',
        params,
    });
}