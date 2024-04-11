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

function getOrderDetail(id) {
    return $axios({
        url: `/order/${id}`,
        method: 'get',
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

function createOrder(params) {
    return $axios({
        url: `/order/save`,
        method: 'post',
        data: params,
    });
}

function payOrder(id) {
    return $axios({
        url: `/order/${id}/pay`,
        method: 'post',
    });
}

function updateOrderStatus(id, params) {
    return $axios({
        url: `/order/${id}/updateStatus`,
        method: 'post',
        params,
    });
}

function getOrdersByLogisticProviderId(id, params) {
    return $axios({
        url: `/order/logisticProvider/${id}`,
        method: 'get',
        params,
    });
}