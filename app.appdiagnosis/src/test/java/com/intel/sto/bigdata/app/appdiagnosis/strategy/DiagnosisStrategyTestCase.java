package com.intel.sto.bigdata.app.appdiagnosis.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisContext;
import com.intel.sto.bigdata.app.appdiagnosis.DiagnosisResult;
import com.intel.sto.bigdata.app.appdiagnosis.util.Constants;
import junit.framework.TestCase;

public abstract class DiagnosisStrategyTestCase extends TestCase {
  List<DiagnosisResult> result;
  DiagnosisContext ac;

  protected void setUp() throws Exception {
    Map<String, List<Map<String, List<List<String>>>>> map =
        new HashMap<String, List<Map<String, List<List<String>>>>>();
    List<Map<String, List<List<String>>>> testDstat_1 =
        new ArrayList<Map<String, List<List<String>>>>();
    String data_1 =
        "1399447552.0,158449664.0,1640624128.0,47380520960.0,0.021,0.969,0.729,0.119,99.710,0.038,0.004,0.0,0.0,0.0,0.0,0.0,0.0,0.0,672.836,28986.126";
    testDstat_1.add(dataFormat(data_1));
    data_1 =
        "1398546432.0,158449664.0,1640624128.0,47381422080.0,0.0,1.0,0.031,0.094,99.875,0.0,0.0,0.0,330.0,0.0,2187.500,1651.500,2517.500,1651.500,0.0,38912.0";
    testDstat_1.add(dataFormat(data_1));
    data_1 =
        "1398407168.0,158449664.0,1640624128.0,47381561344.0,0.0,0.0,0.031,0.094,99.875,0.0,0.0,0.0,655.0,0.0,712.500,1651.500,1367.500,1651.500,0.0,0.0";
    testDstat_1.add(dataFormat(data_1));
    data_1 =
        "18605604864.0,155201536.0,2696347648.0,29121822720.0,0.0,63.0,68.711,1.314,29.599,0.094,0.282,0.0,300.0,0.0,9229215.0,47315647.500,9229515.0,47315647.500,0.0,30568448.0";
    testDstat_1.add(dataFormat(data_1));
    data_1 =
        "46912536576.0,130584576.0,3224485888.0,311369728.0,0.0,0.0,22.858,0.594,76.360,0.0,0.188,0.0,270.0,0.0,127958.500,43522005.0,128228.500,43522005.0,0.0,0.0";
    testDstat_1.add(dataFormat(data_1));

    map.put("sr161", testDstat_1);

    List<Map<String, List<List<String>>>> testDstat_2 =
        new ArrayList<Map<String, List<List<String>>>>();
    String data_2 =
        "1460006912.0,97415168.0,1500721152.0,47520833536.0,0.119,0.995,0.184,0.122,99.668,0.022,0.004,0.0,0.0,0.0,0.0,0.0,0.0,0.0,7877.866,41002.545";
    testDstat_2.add(dataFormat(data_2));
    data_2 =
        "1458868224.0,97419264.0,1500901376.0,47521787904.0,0.0,1.0,0.0,0.094,99.906,0.0,0.0,0.0,595.0,0.0,2187.500,1651.0,2782.500,1651.0,0.0,30720.0";
    testDstat_2.add(dataFormat(data_2));
    data_2 =
        "1458561024.0,97419264.0,1500901376.0,47522095104.0,0.0,0.0,0.031,0.094,99.875,0.0,0.0,0.0,685.0,0.0,712.500,1651.0,1397.500,1651.0,0.0,0.0";
    testDstat_2.add(dataFormat(data_2));
    data_2 =
        "21199638528.0,158040064.0,1370181632.0,27851182080.0,0.0,52.500,20.965,43.027,34.911,1.003,0.094,0.0,655.0,0.0,1302.500,1651.500,1957.500,1651.500,0.0,3807232.0";
    testDstat_2.add(dataFormat(data_2));
    data_2 =
        "39810723840.0,158056448.0,1880989696.0,8729272320.0,0.0,18.0,60.847,5.877,19.931,0.063,0.282,0.0,300.0,0.0,43493993.0,127936.500,43494293.0,127936.500,0.0,1941504.0";
    testDstat_2.add(dataFormat(data_2));

    map.put("sr162", testDstat_2);

    List<Map<String, List<List<String>>>> testDstat_3 =
        new ArrayList<Map<String, List<List<String>>>>();
    String data_3 =
        "19959406592.0,97763328.0,2277617664.0,28244189184.0,65.0,0.0,79.049,9.694,10.538,0.563,0.156,0.0,330.0,0.0,900.500,1428.0,1230.500,1428.0,6836224.0,0.0";
    testDstat_3.add(dataFormat(data_3));
    data_3 =
        "20088610816.0,97771520.0,2479579136.0,27913015296.0,16.500,137.0,37.723,3.065,14.451,44.667,0.094,0.0,150.0,0.0,1007.500,1651.0,1157.500,1651.0,1409024.0,47525888.0";
    testDstat_3.add(dataFormat(data_3));
    data_3 =
        "43604451328.0,97927168.0,3322146816.0,3554451456.0,0.0,0.0,98.531,1.250,0.094,0.031,0.094,0.0,210.0,0.0,35480.500,18374.500,35690.500,18374.500,0.0,0.0";
    testDstat_3.add(dataFormat(data_3));
    data_3 =
        "38823677952.0,97808384.0,3089108992.0,8568381440.0,0.0,0.0,97.247,2.096,0.563,0.0,0.094,0.0,120.0,0.0,16530.500,8869.500,16650.500,8869.500,0.0,0.0";
    testDstat_3.add(dataFormat(data_3));
    data_3 =
        "30380371968.0,97800192.0,2849091584.0,17251713024.0,0.0,2.0,24.734,55.636,19.443,0.063,0.125,0.0,475.0,0.0,744.500,1683.0,1219.500,1683.0,0.0,770048.0";
    testDstat_3.add(dataFormat(data_3));

    map.put("sr163", testDstat_3);

    List<Map<String, List<List<String>>>> testDstat_4 =
        new ArrayList<Map<String, List<List<String>>>>();
    String data_4 =
        "17105072128.0,158470144.0,1154060288.0,32161439744.0,0.0,0.0,98.531,0.938,0.500,0.0,0.031,0.0,330.0,0.0,15506.500,8650.500,15836.500,8650.500,0.0,0.0";
    testDstat_4.add(dataFormat(data_4));
    data_4 =
        "31701143552.0,158470144.0,1575677952.0,17143750656.0,0.0,2.0,91.961,1.783,5.974,0.219,0.063,0.0,330.0,0.0,30410.500,15807.0,30740.500,15807.0,0.0,604160.0";
    testDstat_4.add(dataFormat(data_4));
    data_4 =
        "36447612928.0,158474240.0,2151714816.0,11821240320.0,0.0,0.0,21.370,4.337,72.627,0.031,1.634,0.0,180.0,0.0,55720943.500,83478153.0,55721123.500,83478153.0,0.0,0.0";
    testDstat_4.add(dataFormat(data_4));
    data_4 =
        "36393926656.0,158474240.0,1723912192.0,12302729216.0,0.0,22.500,39.267,1.786,53.526,5.139,0.282,0.0,217.0,27.0,32931970.0,33487267.0,32932187.0,33487294.0,0.0,9633792.0";
    testDstat_4.add(dataFormat(data_4));
    data_4 =
        "17105072128.0,158470144.0,1154060288.0,32161439744.0,0.0,0.0,98.531,0.938,0.500,0.0,0.031,0.0,330.0,0.0,15506.500,8650.500,15836.500,8650.500,0.0,0.0";
    testDstat_4.add(dataFormat(data_4));

    map.put("sr164", testDstat_4);

    List<Map<String, List<List<String>>>> testDstat_5 =
        new ArrayList<Map<String, List<List<String>>>>();
    String data_5 =
        "2158030848.0,158466048.0,1351606272.0,46910939136.0,0.0,0.0,10.006,0.438,89.525,0.0,0.031,0.0,180.0,0.0,4333.500,2152.500,4513.500,2152.500,0.0,0.0";
    testDstat_5.add(dataFormat(data_5));
    data_5 =
        "31744073728.0,158470144.0,1693372416.0,16983126016.0,0.0,0.0,87.219,1.031,11.688,0.0,0.062,0.0,390.0,21.0,21187.0,28359.500,21577.0,28380.500,0.0,0.0";
    testDstat_5.add(dataFormat(data_5));
    data_5 =
        "36384509952.0,158474240.0,1723842560.0,12312215552.0,0.0,176.500,11.417,0.813,85.862,1.783,0.125,0.0,701.0,0.0,3228681.500,15715864.0,3229382.500,15715864.0,0.0,36464640.0";
    testDstat_5.add(dataFormat(data_5));
    data_5 =
        "36393926656.0,158474240.0,1723912192.0,12302729216.0,0.0,22.500,39.267,1.786,53.526,5.139,0.282,0.0,217.0,27.0,32931970.0,33487267.0,32932187.0,33487294.0,0.0,9633792.0";
    testDstat_5.add(dataFormat(data_5));
    data_5 =
        "36393586688.0,158474240.0,1723932672.0,12303048704.0,0.0,4.500,0.532,0.501,97.372,1.471,0.125,0.0,330.0,0.0,10216176.0,3051904.500,10216506.0,3051904.500,0.0,2072576.0";
    testDstat_5.add(dataFormat(data_5));

    map.put("sr165", testDstat_5);

    ac = new DiagnosisContext();
    ac.putPerformanceData(map);

  }

  public Map<String, List<List<String>>> dataFormat(String data) {
    String dataArray[] = data.split(",");
    List<String> record = new ArrayList<String>();
    for (String i : dataArray) {
      record.add(i);
    }
    List<List<String>> dataTable = new ArrayList<List<String>>();
    dataTable.add(record);
    Map<String, List<List<String>>> dataMap = new HashMap<String, List<List<String>>>();
    dataMap.put(Constants.NULL, dataTable);
    return dataMap;
  }
}
