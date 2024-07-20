document.addEventListener('DOMContentLoaded', function() {
  const searchForm = document.getElementById('search-form');
  const jobData = document.getElementById('job-data');
  const allProjectsRadio = document.getElementById('allProjects');
  const activeProjectsRadio = document.getElementById('activeProjects');
  const completedProjectsRadio = document.getElementById('completedProjects');

  function fetchAndDisplayData() {
    let url = '/activeFilter?filter=all';

    if (activeProjectsRadio.checked) {
      url = '/activeFilter?filter=active';
    } else if (completedProjectsRadio.checked) {
      url = '/activeFilter?filter=completed';
    }

    fetch(url)
      .then(response => response.json())
      .then(data => {
        jobData.innerHTML = ''; // Clear existing rows
        data.forEach(project => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${project.client_name}</td>
            <td>${project.job_code}</td>
            <td>${project.status}</td>
            <td>${project.date_issued}</td>
            <td>${project.date_confirmed}</td>
            <td>${project.running_days}</td>
            <td>${project.warranty}</td>
          `;
          jobData.appendChild(row);
        });
      })
      .catch(error => console.error('Error fetching data:', error));
  }

  searchForm.addEventListener('submit', function(event) {
    event.preventDefault();
    fetchAndDisplayData();
  });

  allProjectsRadio.addEventListener('change', fetchAndDisplayData);
  activeProjectsRadio.addEventListener('change', fetchAndDisplayData);
  completedProjectsRadio.addEventListener('change', fetchAndDisplayData);

  fetchAndDisplayData(); // Initial data fetch and display
});
