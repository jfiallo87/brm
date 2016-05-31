(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('facility-event-catalog', {
            parent: 'entity',
            url: '/facility-event-catalog',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventCatalogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-catalog/facility-event-catalogs.html',
                    controller: 'FacilityEventCatalogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('facility-event-catalog-detail', {
            parent: 'entity',
            url: '/facility-event-catalog/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventCatalog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-catalog/facility-event-catalog-detail.html',
                    controller: 'FacilityEventCatalogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FacilityEventCatalog', function($stateParams, FacilityEventCatalog) {
                    return FacilityEventCatalog.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('facility-event-catalog.new', {
            parent: 'facility-event-catalog',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-catalog/facility-event-catalog-dialog.html',
                    controller: 'FacilityEventCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                minGuests: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('facility-event-catalog', null, { reload: true });
                }, function() {
                    $state.go('facility-event-catalog');
                });
            }]
        })
        .state('facility-event-catalog.edit', {
            parent: 'facility-event-catalog',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-catalog/facility-event-catalog-dialog.html',
                    controller: 'FacilityEventCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FacilityEventCatalog', function(FacilityEventCatalog) {
                            return FacilityEventCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facility-event-catalog.delete', {
            parent: 'facility-event-catalog',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-catalog/facility-event-catalog-delete-dialog.html',
                    controller: 'FacilityEventCatalogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FacilityEventCatalog', function(FacilityEventCatalog) {
                            return FacilityEventCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
