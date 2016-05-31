(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('ServiceCatalogDetailController', ServiceCatalogDetailController);

    ServiceCatalogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ServiceCatalog', 'FacilityEventServiceCatalog'];

    function ServiceCatalogDetailController($scope, $rootScope, $stateParams, entity, ServiceCatalog, FacilityEventServiceCatalog) {
        var vm = this;

        vm.serviceCatalog = entity;

        var unsubscribe = $rootScope.$on('brmApp:serviceCatalogUpdate', function(event, result) {
            vm.serviceCatalog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
