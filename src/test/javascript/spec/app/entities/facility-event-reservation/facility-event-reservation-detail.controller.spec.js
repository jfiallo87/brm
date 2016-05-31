'use strict';

describe('Controller Tests', function() {

    describe('FacilityEventReservation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFacilityEventReservation, MockCustomer, MockFacilityEventServiceReservation, MockFacilityEventSeatingCatalog;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFacilityEventReservation = jasmine.createSpy('MockFacilityEventReservation');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockFacilityEventServiceReservation = jasmine.createSpy('MockFacilityEventServiceReservation');
            MockFacilityEventSeatingCatalog = jasmine.createSpy('MockFacilityEventSeatingCatalog');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'FacilityEventReservation': MockFacilityEventReservation,
                'Customer': MockCustomer,
                'FacilityEventServiceReservation': MockFacilityEventServiceReservation,
                'FacilityEventSeatingCatalog': MockFacilityEventSeatingCatalog
            };
            createController = function() {
                $injector.get('$controller')("FacilityEventReservationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'brmApp:facilityEventReservationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
