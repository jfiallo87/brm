'use strict';

describe('Controller Tests', function() {

    describe('SeatingCatalog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSeatingCatalog, MockFacilityEventSeatingCatalog;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSeatingCatalog = jasmine.createSpy('MockSeatingCatalog');
            MockFacilityEventSeatingCatalog = jasmine.createSpy('MockFacilityEventSeatingCatalog');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SeatingCatalog': MockSeatingCatalog,
                'FacilityEventSeatingCatalog': MockFacilityEventSeatingCatalog
            };
            createController = function() {
                $injector.get('$controller')("SeatingCatalogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'brmApp:seatingCatalogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
