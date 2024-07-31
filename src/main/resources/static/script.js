document.addEventListener('DOMContentLoaded', function() {
  const searchForm = document.getElementById('search-form');
  const jobData = document.getElementById('job-data');
  const allProjectsRadio = document.getElementById('allProjects');
  const activeProjectsRadio = document.getElementById('activeProjects');
  const completedProjectsRadio = document.getElementById('completedProjects');
  const rowsPerPage = 20;
  let currentPage = 1;
  let jobDataArray = [];
  const pagination = document.getElementById('pagination');

  function fetchAndDisplayData() {
    let filter = 'all'; // Default filter

    if (activeProjectsRadio.checked) {
      filter = 'active'; // Set filter to 'active'
    } else if (completedProjectsRadio.checked) {
      filter = 'completed'; // Set filter to 'completed'
    }

    const url = `/activeFilter?filter=${filter}`;
    console.log('Fetching URL:', url); // Log URL for debugging

    fetch(url)
      .then(response => response.json())
      .then(data => {
        console.log('Data received:', data); // Log data for debugging
        jobDataArray = data;
        currentPage = 1; // Reset to first page when new data is fetched
        renderTable();
        renderPagination();
      })
      .catch(error => console.error('Error fetching data:', error));
  }

  function renderTable() {
    jobData.innerHTML = ''; // Clear existing rows
    const start = (currentPage - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedData = jobDataArray.slice(start, end);

    paginatedData.forEach(project => {
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
  }

  function renderPagination() {
    pagination.innerHTML = ''; // Clear existing pagination
    const totalPages = Math.ceil(jobDataArray.length / rowsPerPage);

    // Previous Page Button
    if (currentPage > 1) {
      const prev = document.createElement('li');
      prev.className = 'page-item';
      prev.innerHTML = `<a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>`;
      prev.addEventListener('click', function(e) {
        e.preventDefault();
        if (currentPage > 1) {
          currentPage--;
          renderTable();
          renderPagination();
        }
      });
      pagination.appendChild(prev);
    }

    // Page Number Buttons
    for (let i = 1; i <= totalPages; i++) {
      const li = document.createElement('li');
      li.className = `page-item${i === currentPage ? ' active' : ''}`;
      li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
      li.addEventListener('click', function(e) {
        e.preventDefault();
        currentPage = i;
        renderTable();
        renderPagination();
      });
      pagination.appendChild(li);
    }

    // Next Page Button
    if (currentPage < totalPages) {
      const next = document.createElement('li');
      next.className = 'page-item';
      next.innerHTML = `<a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>`;
      next.addEventListener('click', function(e) {
        e.preventDefault();
        if (currentPage < totalPages) {
          currentPage++;
          renderTable();
          renderPagination();
        }
      });
      pagination.appendChild(next);
    }
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
