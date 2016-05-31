(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventServiceCatalogDetailController', FacilityEventServiceCatalogDetailController);

    FacilityEventServiceCatalogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'FacilityEventServiceCatalog', 'ServiceCatalog', 'FacilityEventCatalog', 'FacilityEventServiceReservation'];

    function FacilityEventServiceCatalogDetailController($scope, $rootScope, $stateParams, entity, FacilityEventServiceCatalog, ServiceCatalog, FacilityEventCatalog, FacilityEventServiceReservation) {
        var vm = this;

        vm.facilityEventServiceCatalog = entity;

        var unsubscribe = $rootScope.$on('brmApp:facilityEventServiceCatalogUpdate', function(event, result) {
            vm.facilityEventServiceCatalog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
