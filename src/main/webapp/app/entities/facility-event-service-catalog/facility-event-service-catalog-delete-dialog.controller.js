(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventServiceCatalogDeleteController',FacilityEventServiceCatalogDeleteController);

    FacilityEventServiceCatalogDeleteController.$inject = ['$uibModalInstance', 'entity', 'FacilityEventServiceCatalog'];

    function FacilityEventServiceCatalogDeleteController($uibModalInstance, entity, FacilityEventServiceCatalog) {
        var vm = this;

        vm.facilityEventServiceCatalog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FacilityEventServiceCatalog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
