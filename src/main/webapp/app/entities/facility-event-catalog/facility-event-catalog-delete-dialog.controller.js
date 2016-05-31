(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventCatalogDeleteController',FacilityEventCatalogDeleteController);

    FacilityEventCatalogDeleteController.$inject = ['$uibModalInstance', 'entity', 'FacilityEventCatalog'];

    function FacilityEventCatalogDeleteController($uibModalInstance, entity, FacilityEventCatalog) {
        var vm = this;

        vm.facilityEventCatalog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FacilityEventCatalog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
