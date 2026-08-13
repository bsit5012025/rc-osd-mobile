package org.rocs.osda.mobile.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.rocs.osda.mobile.ui.common.InitialsBadge
import org.rocs.osda.mobile.ui.common.OsdaCard
import org.rocs.osda.mobile.ui.common.StatCard
import org.rocs.osda.mobile.ui.common.StatRow
import org.rocs.osda.mobile.ui.theme.OsdaTokens

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val state by viewModel.uiState.collectAsState()
    val enrollment = state.enrollment
    val person = enrollment?.student?.person
    val fullName = person?.fullName ?: state.studentId ?: "Student"
    val initials = fullName.split(" ").filter { it.isNotBlank() }.take(2).map { it.first().uppercaseChar() }.joinToString("")
    val program = listOfNotNull(enrollment?.student?.studentType, enrollment?.section).joinToString(" ").ifBlank { "—" }
    val guardianContact = state.guardians.firstOrNull()?.contactNumber ?: "Not on file"

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 16.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                InitialsBadge(initials.ifBlank { "?" }, modifier = Modifier.size(44.dp))
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(fullName.uppercase(), color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "Student ID: ${state.studentId ?: "—"}  •  $program",
                        color = OsdaTokens.primaryMuted,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 18.dp)) {
            StatCard(state.violationsCount.toString(), "VIOLATIONS", MaterialTheme.colorScheme.onBackground, Modifier.weight(1f))
            StatCard(state.pendingAppealsCount.toString(), "PENDING APPEALS", OsdaTokens.amber, Modifier.weight(1f))
            StatCard(enrollment?.studentLevel ?: "—", "YEAR/LEVEL", MaterialTheme.colorScheme.onBackground, Modifier.weight(1f))
        }

        Text("Personal Information", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(bottom = 8.dp))
        OsdaCard(modifier = Modifier.padding(bottom = 18.dp)) {
            StatRow("Name", fullName)
            Spacer2()
            StatRow("Date of Birth", person?.dateOfBirth ?: "Not on file")
            Spacer2()
            StatRow("Contact Number", enrollment?.student?.contactNumber ?: "Not on file")
            Spacer2()
            StatRow("Guardian's Contact", guardianContact)
            Spacer2()
            StatRow("Section", enrollment?.section ?: "—")
        }

        Text("Account", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(bottom = 8.dp))
        OsdaCard(modifier = Modifier.padding(bottom = 18.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Change Password", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodySmall)
                Text("›", color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
            }
        }

        Text("Support", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(bottom = 8.dp))
        OsdaCard(modifier = Modifier.padding(bottom = 18.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Student Handbook", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodySmall)
                Text("›", color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun Spacer2() {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(6.dp))
}