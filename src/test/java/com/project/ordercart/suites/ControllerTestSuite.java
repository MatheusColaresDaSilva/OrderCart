package com.project.ordercart.suites;

import com.project.ordercart.integration.CardapioControllerTest;

import com.project.ordercart.integration.ItemControllerTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({CardapioControllerTest.class,
                ItemControllerTest.class})
public class ControllerTestSuite {

}
