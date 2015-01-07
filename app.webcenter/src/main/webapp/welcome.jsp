<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
  <style type="text/css">
    #apps_paginate span {font-weight:normal}
    #apps .progress {width:8em}
    #apps_processing {top:-1.5em; font-size:1em;
      color:#000; background:rgba(255, 255, 255, 0.8)}
    #apps .queue {width:6em}
    #apps .ui {width:8em}
  </style>
  <title>
    Dew Web Center
  </title>
  <link rel="stylesheet" href="/static/yarn.css">
  <style type="text/css">
    #layout { height: 100%; }
    #layout thead td { height: 3em; }
    #layout #navcell { width: 11em; padding: 0 1em; }
    #layout td.content { padding-top: 0 }
    #layout tbody { vertical-align: top; }
    #layout tfoot td { height: 4em; }
  </style>
  <link rel="stylesheet" href="/static/jquery-ui.css">
  <link rel="stylesheet" href="/static/jui-dt.css">
  <script type="text/javascript" src="/static/jquery-1.8.2.min.js">
  </script>
  <script type="text/javascript" src="/static/jquery-ui-1.9.1.custom.min.js">
  </script>
  <script type="text/javascript" src="/static/jquery.dataTables.min.js">
  </script>
  <script type="text/javascript" src="/static/yarn.dt.plugins.js">
  </script>
  <style type="text/css">
    #jsnotice { padding: 0.2em; text-align: center; }
    .ui-progressbar { height: 1em; min-width: 5em }
  </style>
  <script type="text/javascript">
    $(function() {
      $('#nav').accordion({autoHeight:false, active:0});
    appsDataTable =  $('#apps').dataTable({bStateSave : true, "fnStateSave": function (oSettings, oData) { sessionStorage.setItem( oSettings.sTableId, JSON.stringify(oData) ); }, "fnStateLoad": function (oSettings) { return JSON.parse( sessionStorage.getItem(oSettings.sTableId) );}, bJQueryUI:true, sPaginationType: 'full_numbers', iDisplayLength:20, aLengthMenu:[20, 40, 60, 80, 100], 'aaData': appsTableData, bDeferRender: true, bProcessing: true
, aoColumnDefs: [
{'sType':'numeric', 'aTargets': [0], 'mRender': parseHadoopID }
, {'sType':'numeric', 'aTargets': [5, 6], 'mRender': renderHadoopDate }
, {'sType':'numeric', bSearchable:false, 'aTargets': [9], 'mRender': parseHadoopProgress }], aaSorting: [[0, 'desc']]}).fnSetFilteringDelay(188);
    });
  </script>
  <div id="jsnotice" class="ui-state-error">
    This page works best with javascript enabled.
  </div>
  <script type="text/javascript">
    $('#jsnotice').hide();
  </script>
  <table id="layout" class="ui-widget-content">
    <thead>
      <tr>
        <td colspan="2">
          <div id="header" class="ui-widget">
            <div id="user">
              Logged in as: dr.who
            </div>
            <div id="logo">
              <img src="/static/title.jpg">
            </div>
            <h1>
              Dew Web Center
            </h1>
          </div>
    <tfoot>
      <tr>
        <td colspan="2">
          <div id="footer" class="ui-widget">
            <a href="http://hadoop.apache.org/">About Apache Hadoop</a>
          </div>
    <tbody>
      <tr>
        <td id="navcell">
          <div id="nav">
            <h3>
              Cluster
            </h3>
            <ul>
              <li>
                <a href="/cluster/cluster">About</a>
              <li>
                <a href="/cluster/nodes">Nodes</a>
              <li>
                <a href="/cluster/apps">Applications</a>
                <ul>
                  <li>
                  <li>
                    <a href="/cluster/apps/NEW">NEW</a>
                  <li>
                    <a href="/cluster/apps/NEW_SAVING">NEW_SAVING</a>
                  <li>
                    <a href="/cluster/apps/SUBMITTED">SUBMITTED</a>
                  <li>
                    <a href="/cluster/apps/ACCEPTED">ACCEPTED</a>
                  <li>
                    <a href="/cluster/apps/RUNNING">RUNNING</a>
                  <li>
                    <a href="/cluster/apps/REMOVING">REMOVING</a>
                  <li>
                    <a href="/cluster/apps/FINISHING">FINISHING</a>
                  <li>
                    <a href="/cluster/apps/FINISHED">FINISHED</a>
                  <li>
                    <a href="/cluster/apps/FAILED">FAILED</a>
                  <li>
                    <a href="/cluster/apps/KILLED">KILLED</a>
                </ul>
              <li>
                <a href="/cluster/scheduler">Scheduler</a>
            </ul>
            <h3>
              Tools
            </h3>
            <ul>
              <li>
                <a href="/conf">Configuration</a>
              <li>
                <a href="/logs">Local logs</a>
              <li>
                <a href="/stacks">Server stacks</a>
              <li>
                <a href="/metrics">Server metrics</a>
            </ul>
          </div>
        <td class="content">
          <style type="text/css">
            .metrics {margin-bottom:5px}
          </style>
          <div class="metrics">
            <h3>
              Cluster Metrics
            </h3>
            <table id="metricsoverview">
              <thead class="ui-widget-header">
                <tr>
                  <th class="ui-state-default">
                    Apps Submitted
                  <th class="ui-state-default">
                    Apps Pending
                  <th class="ui-state-default">
                    Apps Running
                  <th class="ui-state-default">
                    Apps Completed
                  <th class="ui-state-default">
                    Containers Running
                  <th class="ui-state-default">
                    Memory Used
                  <th class="ui-state-default">
                    Memory Total
                  <th class="ui-state-default">
                    Memory Reserved
                  <th class="ui-state-default">
                    Active Nodes
                  <th class="ui-state-default">
                    Decommissioned Nodes
                  <th class="ui-state-default">
                    Lost Nodes
                  <th class="ui-state-default">
                    Unhealthy Nodes
                  <th class="ui-state-default">
                    Rebooted Nodes
              <tbody class="ui-widget-content">
                <tr>
                  <td>
                    20
                  <td>
                    0
                  <td>
                    0
                  <td>
                    20
                  <td>
                    0
                  <td>
                    0 B
                  <td>
                    371.09 GB
                  <td>
                    0 B
                  <td>
                    <a href="/cluster/nodes">2</a>
                  <td>
                    <a href="/cluster/nodes/decommissioned">0</a>
                  <td>
                    <a href="/cluster/nodes/lost">0</a>
                  <td>
                    <a href="/cluster/nodes/unhealthy">0</a>
                  <td>
                    <a href="/cluster/nodes/rebooted">0</a>
              </tbody>
            </table>
          </div>
          <table id="apps">
            <thead>
              <tr>
                <th class="id">
                  ID
                <th class="user">
                  User
                <th class="name">
                  Name
                <th class="type">
                  Application Type
                <th class="queue">
                  Queue
                <th class="starttime">
                  StartTime
                <th class="finishtime">
                  FinishTime
                <th class="state">
                  State
                <th class="finalstatus">
                  FinalStatus
                <th class="progress">
                  Progress
                <th class="ui">
                  Tracking UI
            <script type="text/javascript">
              var appsTableData=[
["<a href='/cluster/app/application_1420349730444_0009'>application_1420349730444_0009</a>","liyezhan","ScalaWordCount","SPARK","default","1420358152407","1420358198206","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0009/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0016'>application_1420349730444_0016</a>","liyezhan","ScalaWordCount","SPARK","default","1420363552236","1420363739356","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0016/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0015'>application_1420349730444_0015</a>","liyezhan","ScalaWordCount","SPARK","default","1420362828687","1420363284018","FINISHED","FAILED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0015/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0014'>application_1420349730444_0014</a>","liyezhan","ScalaWordCount","SPARK","default","1420362438743","1420362651656","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0014/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0021'>application_1420349730444_0021</a>","liyezhan","ScalaWordCount","SPARK","default","1420365557762","1420365620169","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0021/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0004'>application_1420349730444_0004</a>","liyezhan","ScalaWordCount","SPARK","default","1420355193379","1420355598417","FINISHED","FAILED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0004/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0013'>application_1420349730444_0013</a>","liyezhan","ScalaWordCount","SPARK","default","1420361573981","1420361636853","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0013/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0006'>application_1420349730444_0006</a>","liyezhan","ScalaWordCount","SPARK","default","1420357747508","1420357753525","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0006/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0003'>application_1420349730444_0003</a>","liyezhan","ScalaWordCount","SPARK","default","1420355090162","1420355152754","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0003/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0011'>application_1420349730444_0011</a>","liyezhan","ScalaWordCount","SPARK","default","1420358500337","1420358537242","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0011/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0020'>application_1420349730444_0020</a>","liyezhan","ScalaWordCount","SPARK","default","1420365091052","1420365140151","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0020/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0019'>application_1420349730444_0019</a>","liyezhan","ScalaWordCount","SPARK","default","1420364372119","1420364812709","FINISHED","FAILED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0019/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0005'>application_1420349730444_0005</a>","liyezhan","ScalaWordCount","SPARK","default","1420355929487","1420356384911","FINISHED","FAILED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0005/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0012'>application_1420349730444_0012</a>","liyezhan","ScalaWordCount","SPARK","default","1420359718578","1420359759310","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0012/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0017'>application_1420349730444_0017</a>","liyezhan","ScalaWordCount","SPARK","default","1420364165419","1420364350399","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0017/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0018'>application_1420349730444_0018</a>","liyezhan","ScalaWordCount","SPARK","default","1420364289684","1420364563370","FAILED","FAILED","<br title='0.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='0.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:0.0%'> </div> </div>","<a href='http://sr145:8088/cluster/app/application_1420349730444_0018'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0008'>application_1420349730444_0008</a>","liyezhan","ScalaWordCount","SPARK","default","1420357979883","1420358019987","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0008/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0010'>application_1420349730444_0010</a>","liyezhan","ScalaWordCount","SPARK","default","1420358238129","1420358275908","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0010/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0002'>application_1420349730444_0002</a>","liyezhan","ScalaWordCount","SPARK","default","1420354164548","1420354624126","FINISHED","FAILED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0002/A'>History</a>"],
["<a href='/cluster/app/application_1420349730444_0001'>application_1420349730444_0001</a>","liyezhan","ScalaWordCount","SPARK","default","1420349870092","1420349928472","FINISHED","SUCCEEDED","<br title='100.0'> <div class='ui-progressbar ui-widget ui-widget-content ui-corner-all' title='100.0%'> <div class='ui-progressbar-value ui-widget-header ui-corner-left' style='width:100.0%'> </div> </div>","<a href='http://sr145:8088/proxy/application_1420349730444_0001/A'>History</a>"]
]
            </script>
            <tbody>
            </tbody>
          </table>
    </tbody>
  </table>
</html>
