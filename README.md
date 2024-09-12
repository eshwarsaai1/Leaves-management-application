# Leaves Management Application

This application is designed to manage employee leave requests efficiently. It allows employees to apply for leaves, view their leave history, and manage their profile. Managers can oversee their team's leave requests, approve or reject them, and view profiles.

## Features

- **Employee Login**
    - Employees can log in to access the application. User-defined cookies are used to store session data.
    - ![Login Page](Application%20images/Login%20page.png)

- **Dashboard**
    - Accessing the dashboard without logging in will redirect the user to the login page.
    - **My Leaves**
        - View and filter leaves: Pending, All.
        - View a summary of leaves and upcoming leaves.
        - ![My Leaves Tab](Application%20images/My%20leaves%20tab.png)
        - ![Upcoming Leaves](Application%20images/Upcoming%20leaves%20on%20dashboard.png)

- **My Team Leaves**
    - Managers can view team leave requests, filter them by status (Pending, All), and take action (Accept/Reject).
    - ![My Team Leaves Tab](Application%20images/My%20team%20leaves%20tab.png)
    - ![Act on Leave](Application%20images/Act%20on%20leave.png)

- **My Team**
    - Managers can view all employees under their supervision.
    - Employee profiles are displayed in dialog boxes.
    - ![My Team Tab](Application%20images/My%20team%20tab.png)
    - ![My Team Profiles Dialog](Application%20images/My%20team%20profiles%20dialog.png)

- **Apply Leave**
    - Employees can apply for leave with the following validations:
        - Leave type is based on gender, with an indication of the number of leaves left for each type.
        - The 'From' date cannot be before the present date.
        - The 'To' date cannot be before the 'From' date.
        - Total days are calculated by subtracting holidays.
        - An alert is shown if the applied leave days exceed available days.
        - The submit button is activated only when the total days are more than 0.
        - The cancel button clears data and redirects back to the My Leaves page.
        - ![Leave Application](Application%20images/Leave%20applicaton.png)
        - ![Leave Days Validation](Application%20images/Leaves%20days%20validation.png)
        - ![Leave Weekend Validation](Application%20images/Leaves%20weekend%20validation.png)

- **Profile Dialog Box**
    - The profile dialog box contains the employee's profile data and a logout button.
    - ![My Profile Dialog Box](Application%20images/My%20profile%20dialog%20box.png)

- **Logout**
    - Logging out deletes the user's session cookie and redirects them to the login page.
