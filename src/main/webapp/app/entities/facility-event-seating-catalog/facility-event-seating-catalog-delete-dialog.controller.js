(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventSeatingCatalogDeleteController',FacilityEventSeatingCatalogDeleteController);

    FacilityEventSeatingCatalogDeleteController.$inject = ['$uibModalInstance', 'entity', 'FacilityEventSeatingCatalog'];

    function FacilityEventSeatingCatalogDeleteController($uibModalInstance, entity, FacilityEventSeatingCatalog) {
        var vm = this;

        vm.facilityEventSeatingCatalog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FacilityEventSeatingCatalog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
