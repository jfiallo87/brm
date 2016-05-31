'use strict';

describe('Controller Tests', function() {

    describe('FacilityEventSeatingCatalog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFacilityEventSeatingCatalog, MockSeatingCatalog, MockFacilityEventCatalog, MockFacilityEventReservation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFacilityEventSeatingCatalog = jasmine.createSpy('MockFacilityEventSeatingCatalog');
            MockSeatingCatalog = jasmine.createSpy('MockSeatingCatalog');
            MockFacilityEventCatalog = jasmine.createSpy('MockFacilityEventCatalog');
            MockFacilityEventReservation = jasmine.createSpy('MockFacilityEventReservation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'FacilityEventSeatingCatalog': MockFacilityEventSeatingCatalog,
                'SeatingCatalog': MockSeatingCatalog,
                'FacilityEventCatalog': MockFacilityEventCatalog,
                'FacilityEventReservation': MockFacilityEventReservation
            };
            createController = function() {
                $injector.get('$controller')("FacilityEventSeatingCatalogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'brmApp:facilityEventSeatingCatalogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
