(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('facility-event-service-catalog', {
            parent: 'entity',
            url: '/facility-event-service-catalog',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventServiceCatalogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-service-catalog/facility-event-service-catalogs.html',
                    controller: 'FacilityEventServiceCatalogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('facility-event-service-catalog-detail', {
            parent: 'entity',
            url: '/facility-event-service-catalog/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventServiceCatalog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-service-catalog/facility-event-service-catalog-detail.html',
                    controller: 'FacilityEventServiceCatalogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FacilityEventServiceCatalog', function($stateParams, FacilityEventServiceCatalog) {
                    return FacilityEventServiceCatalog.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('facility-event-service-catalog.new', {
            parent: 'facility-event-service-catalog',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-service-catalog/facility-event-service-catalog-dialog.html',
                    controller: 'FacilityEventServiceCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                unitPrice: null,
                                unitType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('facility-event-service-catalog', null, { reload: true });
                }, function() {
                    $state.go('facility-event-service-catalog');
                });
            }]
        })
        .state('facility-event-service-catalog.edit', {
            parent: 'facility-event-service-catalog',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-service-catalog/facility-event-service-catalog-dialog.html',
                    controller: 'FacilityEventServiceCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FacilityEventServiceCatalog', function(FacilityEventServiceCatalog) {
                            return FacilityEventServiceCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-service-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facility-event-service-catalog.delete', {
            parent: 'facility-event-service-catalog',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-service-catalog/facility-event-service-catalog-delete-dialog.html',
                    controller: 'FacilityEventServiceCatalogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FacilityEventServiceCatalog', function(FacilityEventServiceCatalog) {
                            return FacilityEventServiceCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-service-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
