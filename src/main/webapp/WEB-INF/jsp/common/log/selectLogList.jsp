<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

<jsp:include page="../../fragment/pageNavi.jsp"  flush="false"></jsp:include>

<div class="container-fluid">
	<div class="row">
	    <div class="col-lg-4 col-md-12">
	        <div class="card">
	            <div class="card-body">
	                <div class="d-flex align-items-start">
	                    <h4 class="card-title mb-0">Earning Statistics</h4>
	                    <div class="ml-auto">
	                        <div class="dropdown sub-dropdown">
	                            <button class="btn btn-link text-muted dropdown-toggle" type="button"
	                                id="dd1" data-toggle="dropdown" aria-haspopup="true"
	                                aria-expanded="false">
	                                <i data-feather="more-vertical"></i>
	                            </button>
	                            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dd1">
	                                <a class="dropdown-item" href="#">Insert</a>
	                                <a class="dropdown-item" href="#">Update</a>
	                                <a class="dropdown-item" href="#">Delete</a>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	                <div class="row">
	                	<div class="col-md-12">
	                		<canvas id="myChart"></canvas>
<!-- 		                    <div id="main" style="width: 800px; height: 400px;"></div> -->
	                	</div>
	                </div>
	                <ul class="list-inline text-center mt-4 mb-0">
	                    <li class="list-inline-item text-muted font-italic">Earnings for this month</li>
	                </ul>
	            </div>
	        </div>
	    </div>
	    <div class="col-lg-4 col-md-12">
	        <div class="card">
	            <div class="card-body">
	                <h4 class="card-title">Net Income</h4>
	                <div class="net-income mt-4 position-relative" style="height:294px;"></div>
	                <ul class="list-inline text-center mt-5 mb-2">
	                    <li class="list-inline-item text-muted font-italic">Sales for this month</li>
	                </ul>
	            </div>
	        </div>
	    </div>
	    <div class="col-lg-4 col-md-12">
	        <div class="card">
	            <div class="card-body">
	                <h4 class="card-title mb-4">Earning by Location</h4>
	                <div class="" style="height:180px">
	                    <div id="visitbylocate" style="height:100%"></div>
	                </div>
	                <div class="row mb-3 align-items-center mt-1 mt-5">
	                    <div class="col-4 text-right">
	                        <span class="text-muted font-14">India</span>
	                    </div>
	                    <div class="col-5">
	                        <div class="progress" style="height: 5px;">
	                            <div class="progress-bar bg-primary" role="progressbar" style="width: 100%"
	                                aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
	                        </div>
	                    </div>
	                    <div class="col-3 text-right">
	                        <span class="mb-0 font-14 text-dark font-weight-medium">28%</span>
	                    </div>
	                </div>
	                <div class="row mb-3 align-items-center">
	                    <div class="col-4 text-right">
	                        <span class="text-muted font-14">UK</span>
	                    </div>
	                    <div class="col-5">
	                        <div class="progress" style="height: 5px;">
	                            <div class="progress-bar bg-danger" role="progressbar" style="width: 74%"
	                                aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
	                        </div>
	                    </div>
	                    <div class="col-3 text-right">
	                        <span class="mb-0 font-14 text-dark font-weight-medium">21%</span>
	                    </div>
	                </div>
	                <div class="row mb-3 align-items-center">
	                    <div class="col-4 text-right">
	                        <span class="text-muted font-14">USA</span>
	                    </div>
	                    <div class="col-5">
	                        <div class="progress" style="height: 5px;">
	                            <div class="progress-bar bg-cyan" role="progressbar" style="width: 60%"
	                                aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
	                        </div>
	                    </div>
	                    <div class="col-3 text-right">
	                        <span class="mb-0 font-14 text-dark font-weight-medium">18%</span>
	                    </div>
	                </div>
	                <div class="row align-items-center">
	                    <div class="col-4 text-right">
	                        <span class="text-muted font-14">China</span>
	                    </div>
	                    <div class="col-5">
	                        <div class="progress" style="height: 5px;">
	                            <div class="progress-bar bg-success" role="progressbar" style="width: 50%"
	                                aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
	                        </div>
	                    </div>
	                    <div class="col-3 text-right">
	                        <span class="mb-0 font-14 text-dark font-weight-medium">12%</span>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
</div>

<script type="text/javaScript" language="javascript">
var ctx = document.getElementById('myChart');
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});
</script>
