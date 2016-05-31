(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('facility-event-service-reservation', {
            parent: 'entity',
            url: '/facility-event-service-reservation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventServiceReservations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-service-reservation/facility-event-service-reservations.html',
                    controller: 'FacilityEventServiceReservationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('facility-event-service-reservation-detail', {
            parent: 'entity',
            url: '/facility-event-service-reservation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventServiceReservation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-service-reservation/facility-event-service-reservation-detail.html',
                    controller: 'FacilityEventServiceReservationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FacilityEventServiceReservation', function($stateParams, FacilityEventServiceReservation) {
                    return FacilityEventServiceReservation.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('facility-event-service-reservation.new', {
            parent: 'facility-event-service-reservation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-service-reservation/facility-event-service-reservation-dialog.html',
                    controller: 'FacilityEventServiceReservationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('facility-event-service-reservation', null, { reload: true });
                }, function() {
                    $state.go('facility-event-service-reservation');
                });
            }]
        })
        .state('facility-event-service-reservation.edit', {
            parent: 'facility-event-service-reservation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-service-reservation/facility-event-service-reservation-dialog.html',
                    controller: 'FacilityEventServiceReservationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FacilityEventServiceReservation', function(FacilityEventServiceReservation) {
                            return FacilityEventServiceReservation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-service-reservation', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facility-event-service-reservation.delete', {
            parent: 'facility-event-service-reservation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-service-reservation/facility-event-service-reservation-delete-dialog.html',
                    controller: 'FacilityEventServiceReservationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FacilityEventServiceReservation', function(FacilityEventServiceReservation) {
                            return FacilityEventServiceReservation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-service-reservation', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
